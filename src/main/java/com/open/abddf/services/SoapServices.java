package com.open.abddf.services;

import com.open.abddf.config.Config;
import com.open.abddf.context.TestContext;
import com.open.abddf.logger.LoggerClass;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SoapServices {
	
	public TestContext context;
	org.apache.log4j.Logger log;

	public SoapServices() {

	}

	public SoapServices(TestContext context) {
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
	
	public void prepareXmlRequest(String fileName) {
		
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
			if (data != null) {
				val = val.replace("${" + key + "}", data);
			}
		}
		log.info("XML Request After replacing values : " + val);
		//System.out.println("XML Request After replacing values : " + val);
		this.context.setVar("TEMPLATE", val);
	}
	
	public void sendSoapRequest() {
		String requestData = (String)this.context.getVar("TEMPLATE");
		try {
			String region = Config.properties.getProperty("Region");
			String endPintURL = Config.properties.getProperty("EndPointURL_" + region + "_" + this.context.getVar("customerName"));
			log.info("end point url - " + endPintURL);
			CloseableHttpClient client = HttpClients.createDefault();
		    HttpPost httpPost = new HttpPost(endPintURL);
		    StringEntity entity = new StringEntity(requestData);
		    httpPost.setEntity(entity);
		    if (endPintURL != null){
				String header = Config.properties.getProperty("Header_" + region + "_" + this.context.getVar("customerName"));
				//logger.info("end point url - " + header);
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
		    HttpEntity entity1 = response.getEntity();
		    String responseString = EntityUtils.toString(entity1);
			log.info("XML Response  - " + responseString);
			//System.out.println("XML Response  - " + responseString);
			this.context.setVar("responseString", responseString);
			client.close();
		}catch(Exception var1) {
			log.info("response failed " + var1 + System.lineSeparator());
		}
	}
		
}
