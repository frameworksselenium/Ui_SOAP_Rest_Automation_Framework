package com.open.abddf.services;

import com.jayway.jsonpath.JsonPath;
import com.open.abddf.context.TestContext;
import com.open.abddf.logger.LoggerClass;
import com.open.abddf.utilities.CSVData;
import com.open.abddf.utilities.ExcelData;
import org.apache.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ResultsAssertions {

	public int responseStatus;
	public String responseString;
	public ExcelData data;
	CSVData csvData;
	public RestServices restServices;
	XMLUtilitySupport xmlUtilitySupport;
	org.apache.log4j.Logger log;
	public TestContext context;

	public ResultsAssertions(TestContext context) {
		this.context = context;
		xmlUtilitySupport = new XMLUtilitySupport(context);
		csvData = new CSVData(context);
		data = new ExcelData(context);
		restServices = new RestServices(context);
		log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), this.context.getVar("testCaseName").toString());
	}

	public void jsonResponseAssertions(String expectedVal, String scenario) {
		String jsonPath = this.data.readDataForJsonValidations(scenario, expectedVal);
		String jsonFile = this.context.getVar("serviceResponse").toString();
		String result = this.getValueFromJsonFile(jsonFile, jsonPath);
		if (result != null) {
			if (result.startsWith("\"")) {
				result = result.substring(1, result.length() - 1);
			}
			if (expectedVal.equalsIgnoreCase(result.trim())) {
				log.info("PASS:::Verifying status from " + scenario + " Json response. Actual value '" + result + "' Expected value '" + expectedVal + "'" + System.lineSeparator());
			}else {
				log.info("FAIL:::Verifying status from " + scenario + " Json response. Actual value '" + result + "' Expected value '" + expectedVal + "'" + System.lineSeparator());
			}
		}else {
			log.info("Json path " + jsonPath + " is null");
		}
	}

	public void jsonResponseAssertionsCSV(String expectedVal, String nodeName, String jsonPath) {

		//String jsonPath = this.csvData.readDataForJsonPathFromCSV(scenario, expectedVal);
		String jsonFile = this.context.getVar("responseString").toString();
		String result = this.getValueFromJsonFile(jsonFile, jsonPath).trim();
		if (result != null) {
			if (result.equalsIgnoreCase(expectedVal.trim())) {
				log.info("PASS:::Verifying status from " + nodeName + " Json response. Actual value '" + result + "' Expected value '" + expectedVal + "'" + System.lineSeparator());
			}else {
				log.info("FAIL:::Verifying status from " + nodeName + " Json response. Actual value '" + result + "' Expected value '" + expectedVal + "'" + System.lineSeparator());
			}
		}else {
			log.info("Json path " + jsonPath + " is null");
		}
	}

	public String getValueFromJsonFile(String jsonFile, String jsonPath) {
		try {
			JSONParser parser = new JSONParser();
			Object jsonObject = parser.parse(jsonFile);
			String result = JsonPath.read(jsonObject, jsonPath).toString();
			return result;//.substring(result.indexOf("[") + 1, result.indexOf("]"));
		} catch (Exception var5) {
			log.info("Exception - " + var5);
			return null;
		}
	}
	
	public void xmlResponseAssertions(String reportName, String childName, String expectedVal) {
		//String jsonPath = this.data.readDataForJsonValidations(scenario, expectedVal);
		//String jsonFile = this.context.testData.get("serviceResponse");
		boolean isMatch = false;
		String nodeText = null;
		NodeList result =(NodeList) xmlUtilitySupport.getValueFromResponse(this.context.getVar("responseString").toString(), "//" + reportName +  "//" + childName);
		for (int i = 0; i < result.getLength(); i++) {
			Node node = result.item(i);
			nodeText = node.getTextContent();
			if (nodeText.equals(expectedVal)) {
				isMatch = true;
				break;
			}
		}
		if(isMatch) {
			log.info("Pass::: Tag of " + reportName + " in " + childName + " matches with value as '" + expectedVal + "'");
		}else {
			log.info("FAIL::: Tag of " + reportName + " in " + childName + " not matched with value as '" + expectedVal + "'");
		}
	}
}
