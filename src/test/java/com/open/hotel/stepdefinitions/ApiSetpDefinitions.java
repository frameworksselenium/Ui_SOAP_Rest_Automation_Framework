package com.open.hotel.stepdefinitions;

import com.open.abddf.context.TestContext;
import com.open.abddf.services.FlatMapUtil;
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
import java.io.IOException;
import java.util.HashMap;

public class ApiSetpDefinitions {

	private TestContext context;

	ExcelData excelData;
	CSVData csvData;
	SoapServices soapServices;
	RestServices restServices;
	ResultsAssertions resultsAssertions;
	Data data;
	FlatMapUtil flatMapUtil;

	public ApiSetpDefinitions(TestContext context){
		this.context = context;
		excelData = new ExcelData(context);
		csvData = new CSVData(context);
		soapServices = new SoapServices(context);
		restServices = new RestServices(context);
		resultsAssertions = new ResultsAssertions(context);
		data = new Data(context);
		flatMapUtil = new FlatMapUtil(context);
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


	@When("I submit the JSON {string} request with endpoint {string} for {string} service and save response {string}")
	public void I_submit_the_JSON_request(String requestType, String endPoint, String customerName, String variableName){
		this.context.setVar("customerName", customerName);
		if(requestType.contains("POST")){
			this.restServices.getResponseFromPostMethod(this.context.getVar("TEMPLATE").toString(), endPoint);
			this.context.setVar(variableName, this.context.getVar("responseString"));
		}else if(requestType.contains("GET")){
			this.restServices.getResponseFromGetMethod(endPoint);
			this.context.setVar(variableName, this.context.getVar("responseString"));
		}
	}


	@And("Validating {string} from {string} JSON response")
	public void Validating_ExpectedValue_from_JSON_response(String expectedVal, String scenario) {
		this.resultsAssertions.jsonResponseAssertions(expectedVal, scenario);
	}


	//////////////////////CSV Json////////////////////////////////////////////////////////////
	@Given("Customer {string} Read data from {string} with testcase ID {string} and Create JSON request using JSON template {string}")
	public void CSV_Create_JSON_request(String customer, String csvFileName, String testCaseID, String template) {
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
	@When("I submit the xml request")
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


	////compare 2 json files////////////////////////////////////
	@Given("compare 2 {string} responses - {string} and {string}")
	public void compare_2_files(String fileType, String leftFile, String rightFile) throws IOException {
		this.flatMapUtil.compareFiles(fileType, this.context.getVar("response1").toString(), this.context.getVar("response2").toString());
	}

	@Given("compare 2 {string} files which stored in disk - file1 {string} and file2 {string}")
	public void compare_2_json_files_Sample(String fileType, String leftFile, String rightFile) throws IOException {
		this.flatMapUtil.compareFiles(leftFile, rightFile);
	}

	@When("I submit the {string} request and save response {string}")
	public void I_submit_the_xml_request(String formatType, String variableName){
		this.soapServices.sendSoapRequest();
		this.context.setVar(variableName, this.context.getVar("responseString"));
	}

	@Given("compare 2 {string} responses - {string} and {string} using pojo")
	public void compare_2_Json_Responses_Using_Pojo(String fileType, String leftFile, String rightFile) throws IOException {
		this.flatMapUtil.pojoComparision(this.context.getVar("response1").toString(), this.context.getVar("response2").toString());
	}

}