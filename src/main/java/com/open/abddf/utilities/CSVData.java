package com.open.abddf.utilities;

import com.open.abddf.context.TestContext;
import com.open.abddf.logger.LoggerClass;
import com.open.abddf.services.RestServices;
import com.open.abddf.services.SoapServices;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;

public class CSVData {

	LinkedHashMap<String,String> data = new LinkedHashMap<>();
	String dataKey = "";
	BufferedReader br = null;
	public FileInputStream fis = null;
	public FileOutputStream outPut_File = null;
	public RestServices restServices;
	public SoapServices soapServices;
	public TestContext context;
	org.apache.log4j.Logger log;

	public CSVData() {
		
	}

	public CSVData(TestContext context) {
		this.context = context;
		restServices = new RestServices(context);
		soapServices = new SoapServices(context);
		log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), this.context.getVar("testCaseName").toString());
	}

	public void connectionCSV(String csvFileName) {
		try {
			String fileName = csvFileName + ".csv";
			String filePath = System.getProperty("user.dir") + "//src//test//resources//Data//" + fileName;
			this.context.setVar("fis", filePath);
			//File path = null;
			boolean flag = (new File(filePath).exists());
			if (!flag) {
				//path = new File(filePath);
			}
			this.br = new BufferedReader(new FileReader(filePath));
		}catch (Exception var1) {
			log.info("Exception - " + var1);
		}
		finally{
			if (this.br != null){
				try{
					this.br.close();
				}catch (IOException var1){
					log.info("Exception " + var1);
				}
			}
		}
	}

	public void connectionCSV() {
		try {
			//String fileName = "TestData.csv";
			String fileName = this.context.getVar("csvFileName").toString();
			String filePath = System.getProperty("user.dir") + "//src//test//resources//Data//" + fileName;
			this.context.setVar("fis", filePath);
			//File path = null;
			boolean flag = (new File(filePath).exists());
			if (!flag) {
				//path = new File(filePath);
			}
			this.br = new BufferedReader(new FileReader(filePath));
		}catch (Exception var1) {
			log.info("Exception - " + var1);
		}
		finally{
			if (this.br != null){
				try{
					this.br.close();
				}catch (IOException var11){
					log.info("Exception " + var11);
				}
			}
		}
	}

	public void jsonRequestCSV(String customer, String testCaseID, String template) {
		this.connectionCSV();
		Map<String, String> testData = this.readData(testCaseID);
		this.context.setVar("testData", testData);
		log.info(testData + System.lineSeparator());
		this.restServices.restRequest(template);
	}

	public void xmlRequestCSV(String customer, String testCaseID, String template) {
		this.connectionCSV();
		Map<String, String> testData  = this.readData(testCaseID);
		this.context.setVar("testData", testData);
		log.info(testData + System.lineSeparator());
		this.soapServices.prepareXmlRequest(template);
	}

	public HashMap readData(String key){

		HashMap<Integer, String> data = new LinkedHashMap();
		HashMap<String, String> data1 = new LinkedHashMap();
		int counter = 1;
		String valueName = null;
		try{
			Reader in = new FileReader(this.context.getVar("fis").toString());
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			
			for(Iterator var2 = records.iterator(); var2.hasNext(); ++counter){
				CSVRecord record = (CSVRecord)var2.next();
				int j;
				if(counter == 1){
					for(j = 0; j < record.size();++j){
						data.put(j,record.get(j));
					}
				}
				if(key.equalsIgnoreCase(record.get(0))){
					for(j = 0; j < data.size();++j){
						try{
							if(record.get(j).trim().startsWith("#")){
								valueName = record.get(j).substring(1);
								data1.put(data.get(j),valueName);
							}else{
								data1.put(data.get(j),record.get(j));
							}
						}catch(Exception var3){
							data.put(j, (String)null);
						}
					}
				}
			}
		} catch(IOException val1){
			log.info("Exception " + val1);
		}
		return data1;
	}

	public String readDataForJsonPathFromCSV(String customer, String value) {

		int colNumber = 0;
		String data = null;
		FileReader in =null;
		try{
			in = new FileReader((this.context.getVar("fis").toString()));
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			Iterator var2 = records.iterator();
			while(true){
				while(var2.hasNext()){
					CSVRecord record = (CSVRecord)var2.next();
					for(int i = 0; i < record.size();++i){
						if(record.get(i).equalsIgnoreCase(value)){
							colNumber = i;
						}
						if(record.get(0).equalsIgnoreCase(value)){
							data = record.get(1);
							break;
						}
					}
				}
			}
		}catch (Exception var1) {
			log.info("Exception - " + var1);
		}finally{
			if (in != null){
				try{
					in.close();
				}catch (IOException var2){
					log.info(var2.getMessage());
				}
			}
		}
		return data;
	}



}
