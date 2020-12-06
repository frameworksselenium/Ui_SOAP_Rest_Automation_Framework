package com.open.abddf.dataParsers;

import com.open.abddf.context.TestContext;
import com.open.abddf.logger.LoggerClass;
import com.open.abddf.services.RestServices;
import com.open.abddf.services.SoapServices;
import io.cucumber.datatable.DataTable;
import java.io.*;
import java.util.*;

public class Data {

	LinkedHashMap<String,String> data = new LinkedHashMap<>();
	BufferedReader br = null;
	public FileInputStream fis = null;
	public FileOutputStream outPut_File = null;
	public RestServices restServices;
	public SoapServices soapServices;
	public TestContext context;
	org.apache.log4j.Logger log;

	public Data() {

	}

	public Data(TestContext context) {
		this.context = context;
		restServices = new RestServices(context);
		soapServices = new SoapServices(context);
		log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), this.context.getVar("testCaseName").toString());
	}

	public void jsonRequest(String template) {
		Map<String, String> testData = (Map<String, String>)this.context.getVar("testData");
		log.info(testData + System.lineSeparator());
		this.restServices.restRequest(template);
	}

	public void xmlRequest(String template) {
		Map<String, String> testData = (Map<String, String>)this.context.getVar("testData");
		log.info(testData + System.lineSeparator());
		this.soapServices.prepareXmlRequest(template);
	}

	public HashMap<String, String> convertDataTableValuesToList(DataTable dt){
		HashMap<String, String> val = new HashMap<String, String>();
		List<List<String>> list  = dt.asLists(String.class);
		List<Map<String, String>> map  = dt.asMaps(String.class, String.class);
		if(list.get(0).size() != 2){
			throw new RuntimeException("Failed data load");
		}
		for(int i=0; i<list.size();i++){
			val.put(list.get(i).get(0), list.get(i).get(1));
		}
		return val;
	}

}
