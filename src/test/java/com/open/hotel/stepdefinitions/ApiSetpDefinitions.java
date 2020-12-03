package com.open.hotel.stepdefinitions;

import com.open.abddf.context.TestContext;
import com.open.abddf.services.RestServices;
import com.open.abddf.services.ResultsAssertions;
import com.open.abddf.services.SoapServices;
import com.open.abddf.utilities.CSVData;
import com.open.abddf.utilities.Data;
import com.open.abddf.utilities.ExcelData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;

public class ApiSetpDefinitions {

	private TestContext context;

	ExcelData excelData;
	CSVData csvData;
	SoapServices soapServices;
	RestServices restServices;
	ResultsAssertions resultsAssertions;
	Data data;


	public ApiSetpDefinitions(TestContext context){
		this.context = context;
		excelData = new ExcelData(context);
		csvData = new CSVData(context);
		soapServices = new SoapServices(context);
		restServices = new RestServices(context);
		resultsAssertions = new ResultsAssertions(context);
		data = new Data(context);
	}


	//////////////////////Excel Json////////////////////////////////////////////////////////////
	@Given("Customer {string} Create JSON request using JSON template {string} and connect Excel data sheet {string} with testcase ID {string}")
	public void Create_JSON_request(String customerName, String template, String sheet, String testCaseID) {
		this.context.setVar("customerName", customerName);
		this.excelData.jsonRequestExcel(customerName, testCaseID, sheet, template);
	}

	@When("I submit the JSON {string} request with endpoint {string} for {string} service")
	public void I_submit_the_JSON_request(String requestType, String endPoint, String customerName){
		this.context.setVar("customerName", customerName);
		if(requestType.contains("POST")){
			this.restServices.getResponseFromPostMethod(this.context.getVar("TEMPLATE").toString(), endPoint);
		}else if(requestType.contains("GET")){
			this.restServices.getResponseFromGetMethod(endPoint);
		}
	}

	@And("Validating {string} from {string} JSON response")
	public void Validating_ExpectedValue_from_JSON_response(String expectedVal, String scenario) {
		this.resultsAssertions.jsonResponseAssertions(expectedVal, scenario);
	}


	//////////////////////CSV Json////////////////////////////////////////////////////////////
	@Given("Customer {string} Read data from {string} with testcase ID {string} and Create JSON request using JSON template {string}")
	public void CSV_Create_JSON_request(String customer, String csvFileName, String template, String testCaseID) {
		this.context.setVar("customerName", customer);
		this.context.setVar("csvFileName", csvFileName);
		this.csvData.jsonRequestCSV(customer, testCaseID, template);
	}

	@Then("Connect CSV {string}")
	public void Connect_CSV(String csvFileName){
		this.csvData.connectionCSV(csvFileName);
	}

	@When("Validate {string} from {string} JSON response - json path {string}")
	public void Validate_ExpectedValue_from_JSON_response(String expectedVal, String nodeName, String jsonPath) {
		this.resultsAssertions.jsonResponseAssertionsCSV(expectedVal, nodeName, jsonPath);
	}



	//////////////////////Excel XML////////////////////////////////////////////////////////////
	@Given("Customer {string} Create xml request using xml template {string} and connect data sheet {string} with testcase ID {string}")
	public void Create_xml_request(String customerName, String template, String sheet, String testCaseID) {
		this.context.setVar("customerName", customerName);
		this.excelData.xmlRequestExcel(customerName, testCaseID, sheet, template);
	}
	@When("^I submit the xml request$")
	public void I_submit_the_xml_request(){
		this.soapServices.sendSoapRequest();
	}

	@Then("Validating tag {string} of {string} as {string} from XML Response")
	public void Validating_tag_of_as_from_XML_Response(String reportName, String childName, String expectedVal) {
		this.resultsAssertions.xmlResponseAssertions(reportName, childName, expectedVal);
	}

	//////////////////////CSV XML////////////////////////////////////////////////////////////
	@Given("Customer {string} Read data from {string} CSV file with testcase ID {string} and Create XML request using XML template {string}")
	public void CSV_Create_XML_request(String customer, String csvFileName, String testCaseID, String template) {
		this.context.setVar("customerName", customer);
		this.context.setVar("csvFileName", csvFileName);
		this.csvData.xmlRequestCSV(customer, testCaseID, template);
	}


	////Cucumber parameterization for Json ////////////////////////////////////
	@Given("Customer {string} Create JSON request using JSON template {string}")
	public void Create_JSON_request_using_JSON_template(String customerName, String template, DataTable dt) {
		HashMap<String, String> testData = this.data.convertDataTableValuesToList(dt);
		this.context.setVar("testData", testData);
		this.context.setVar("customerName", customerName);
		this.data.jsonRequest(template);
	}


	////Cucumber parameterization for XML ////////////////////////////////////
	@Given("Customer {string} Create XML request using XML template {string}")
	public void Create_XML_request_using_XML_template(String customerName, String template, DataTable dt) {
		HashMap<String, String> testData = this.data.convertDataTableValuesToList(dt);
		this.context.setVar("testData", testData);
		this.context.setVar("customerName", customerName);
		this.data.xmlRequest(template);
	}


}