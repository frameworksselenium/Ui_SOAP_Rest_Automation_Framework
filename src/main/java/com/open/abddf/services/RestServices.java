package com.open.abddf.services;

import com.open.abddf.config.Config;
import com.open.abddf.context.TestContext;
import com.open.abddf.logger.LoggerClass;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RestServices {
	
	public TestContext context;
	org.apache.log4j.Logger log;

	public RestServices() {

	}

	public RestServices(TestContext context) {
		this.context = context;
		log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), this.context.getVar("testCaseName").toString());
	}
	public String loadRequest(String requestName) {
		
		String template = "";
		InputStream is = null;		
		BufferedReader bReader = null;
		FileReader reader = null;
		try {
			String sourcePath =  System.getProperty("user.dir") +  "\\src\\test\\resources\\templates\\" + requestName + ".txt";
			reader = new FileReader(sourcePath);
			bReader = new BufferedReader(reader);
			String newLine = System.lineSeparator();
			String temp = "";
			while ((temp = bReader.readLine()) != null) {
				template = template + temp + newLine;
			   }

		} catch (Exception var29) {
			log.info("exception - " + var29);
		} finally {
			try {
				if (bReader != null) {
					bReader.close();
				}
			} catch(Exception var30) {
				log.info(var30.getMessage());
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch(Exception var31) {
				log.info(var31.getMessage());
			}
			try {
				if (reader != null) {
					reader.close();
				}
			} catch(Exception var32) {
				log.info(var32.getMessage());
			}			
		}
		return template;
	}
	
	public void restRequest(String fileName) {
		
		String val = this.loadRequest(fileName);
		Map<String, String> testData = (Map<String, String>)this.context.getVar("testData");
		Set<String> keyList = testData.keySet();
		String data = "empty";
		String keyAttribute = null;
		Iterator var6 = keyList.iterator();
		
		while(var6.hasNext()) {
			
			String key = (String)var6.next();
			try {
				Map<String, String> testData1 = (Map<String, String>)this.context.getVar("testData");

				data = (String)testData1.get(key);
			}catch(Exception var18) {
				log.info(var18.getMessage());
			}
			
			if (data != null && data.equals("*")) {
				val = val.replace("${" + key + "}", "");
			}else if(data != null) {
				if(data.equals("pending TransactionID")) {	
					//val = val.replace("${" + key + "}", this.context.pendingTransactionID_resubmit);				
				}else {
					val = val.replace("${" + key + "}", data);
				}
			}else {
				
				if (key.contains("_")) {
					String [] actKey = key.split("_");
					keyAttribute = actKey[1];
				}
				
				val = val.replace("\"name\":\"" + key + "\",", "");
				val = val.replace("\"name\":\"" + key + "\"", "");
				
				val = val.replace("\"" + keyAttribute + "\":\"${" + key + "}\",", "");
				val = val.replace("\"" + keyAttribute + "\":\"${" + key + "}\"", "");
				
				val = val.replace("\"value\":\"${" + key + "}\",", "");
				val = val.replace("\"value\":\"${" + key + "}\"", "");

				val = val.replace("\"" + key + "\":\"${" + key + "}\",", "");
				val = val.replace("\"" + key + "\":\"${" + key + "}\"", "");
				
				int fromIndex = 0;
				int lastComma;
				for (lastComma = 0; lastComma < val.length(); ++lastComma) {
						int openIndex = val.indexOf("{", fromIndex);
						int closeIndex = val.indexOf("}", openIndex);
						
						if (closeIndex >= val.length() || closeIndex == -1 || openIndex == -1) {
							break;
						}
						if (val.substring(openIndex +1,  closeIndex -1).trim().isEmpty()) {
							if (val.substring(closeIndex + 1, closeIndex + 2).equals(",")) {
								val = val.replace(val.substring(openIndex, closeIndex + 2), "");
								fromIndex = closeIndex + 2;
							}else {
								val = val.replace(val.substring(openIndex, closeIndex + 1), "");
								fromIndex = closeIndex = 1;
							}						
						}else {
							fromIndex = closeIndex = 1;
						}
				}
				int lastCamma = 0;
				int firstCamma;
				for (firstCamma = 0; firstCamma < val.length(); ++firstCamma) {
					int lastSqBracket = val.indexOf("]", lastCamma);
					if (firstCamma >= val.length() || lastSqBracket == -1 || lastCamma == -1) {
						break;
					}
					
					String subString = val.substring(0, lastSqBracket);
					lastCamma = subString.lastIndexOf(",");
					if (val.substring(lastCamma + 1, lastSqBracket).trim().equalsIgnoreCase("")) {
						val = val.substring(0,lastCamma) + val.substring(lastCamma + 1);
					}
					lastCamma = lastSqBracket +1;
					firstCamma = lastSqBracket +1;
				}
				firstCamma = 0;
				for (int i = 0; i < val.length(); ++i) {
					int firstSqBracket = val.indexOf("[", firstCamma);
					if (i >= val.length() || firstSqBracket == -1 || firstCamma == -1) {
						break;
					}
					String firstSubString = val.substring(firstSqBracket, val.length()); 
					firstCamma = firstSubString.indexOf(",");
					if(firstCamma >= 0 && firstSubString.substring(firstSubString.indexOf("[") + 1, firstCamma).trim().equalsIgnoreCase("")); {
						val = val.substring(0, val.indexOf(",", firstSqBracket)) + val.substring(val.indexOf(",", firstSqBracket) +1);
					}
					firstCamma = firstSqBracket + 1;
					i = firstSqBracket + 1;		
				}
			}
		}	
		log.info(val);
		this.context.setVar("TEMPLATE", val);
	}

	public void getResponseFromPostMethod(String jsonRequest, String endPoint) {
		//https://www.baeldung.com/httpclient-post-http-request
		//https://www.mkyong.com/java/apache-httpclient-examples/
		try {
			String region = Config.properties.getProperty("Region");
			String url = Config.properties.getProperty("EndPointURL_" + region + "_" + this.context.getVar("customerName"));
			log.info("end point url - " + url + endPoint);
			CloseableHttpClient client = HttpClients.createDefault();
		    HttpPost httpPost = new HttpPost(url + endPoint);
		    StringEntity entity = new StringEntity(jsonRequest);
		    httpPost.setEntity(entity);
		    if (url != null){
				String header = Config.properties.getProperty("Header_" + region + "_" + this.context.getVar("customerName"));
				String[] headerValues = header.split(";");
				for(int i = 0; i < headerValues.length; ++i) {
					String[] values = headerValues[i].split(":");
					httpPost.setHeader(values[0],values[1]);	
				}
			}
		    CloseableHttpResponse response = client.execute(httpPost);
		    if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code :" +  response.getStatusLine().getStatusCode());
			}
			log.info("Response Status - " + response.getStatusLine().getStatusCode());
		    HttpEntity entity1 = response.getEntity();
		    String responseString = EntityUtils.toString(entity1);
			log.info("Response  - " + responseString);
			this.context.setVar("responseString", responseString);
			client.close();
		}catch(Exception var1) {
			log.info("response failed " + var1 + System.lineSeparator());
		}
	}

	public void getResponseFromGetMethod(String endPoint) {
		//https://www.baeldung.com/httpclient-post-http-request
		//https://www.mkyong.com/java/apache-httpclient-examples/
		try {
			String region = Config.properties.getProperty("Region");
			String url = Config.properties.getProperty("EndPointURL_" + region + "_" + this.context.getVar("customerName"));
			log.info("end point url - " + url + endPoint);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url + endPoint);

			if (url != null){
				String header = Config.properties.getProperty("Header_" + region + "_" + this.context.getVar("customerName"));
				String[] headerValues = header.split(";");
				for(int i = 0; i < headerValues.length; ++i) {
					String[] values = headerValues[i].split(":");
					httpGet.setHeader(values[0],values[1]);
				}
			}
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code :" +  response.getStatusLine().getStatusCode());
			}
			log.info("Response Status - " + response.getStatusLine().getStatusCode());
			HttpEntity entity1 = response.getEntity();
			String responseString = EntityUtils.toString(entity1);
			log.info("Response  - " + responseString);
			this.context.setVar("responseString", responseString);
			httpClient.close();
		}catch(Exception var1) {
			log.info("response failed " + var1 + System.lineSeparator());
		}
	}
		
}
