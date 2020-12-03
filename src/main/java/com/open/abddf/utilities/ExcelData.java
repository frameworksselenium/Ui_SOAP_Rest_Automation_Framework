package com.open.abddf.utilities;

import com.open.abddf.context.TestContext;
import com.open.abddf.logger.LoggerClass;
import com.open.abddf.services.RestServices;
import com.open.abddf.services.SoapServices;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ExcelData {

	static final Logger logger = Logger.getLogger(ExcelData.class);
	public XSSFWorkbook workbook = null;
	public XSSFSheet sheet = null;
	public XSSFRow row = null;
	public XSSFCell cell = null;
	public FileInputStream fis = null;
	public XSSFRow rowheader = null;
	public FileOutputStream outPut_File = null;
	public File excelPah = null;
	public RestServices restServices;
	public SoapServices soapServices;
	public TestContext context;
	org.apache.log4j.Logger log;
	public ExcelData(TestContext context) {

		this.context = context;
		restServices = new RestServices(context);
		soapServices = new SoapServices(context);
		log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), this.context.getVar("testCaseName").toString());
	}

	public ExcelData() {
	}

	public void connectionExcel(String sheetName) {
		try {
			String fileName = "TestData.xlsx";
			String filePath = System.getProperty("user.dir") + "//src//test//resources//data//" + fileName;
			ClassLoader classLoader = this.getClass().getClassLoader();
			File path = new File(filePath);
			boolean flag = path.exists();
			if (!flag) {
				path = new File(filePath);
			}
			this.fis = new FileInputStream(path);
			this.workbook = new XSSFWorkbook(this.fis);
			this.sheet = this.workbook.getSheet(sheetName);
			this.context.setVar("sheet", this.sheet);
		}catch (Exception var1) {
			log.info("Exception - " + var1);
		}
	}
	public HashMap<String, String> readData(String key, String value){
		HashMap<String, String> data = new HashMap();
		int cellValue = 0;
		int rowValue = 0;
		int rowCount = this.sheet.getPhysicalNumberOfRows();
		this.row = this.sheet.getRow(0);
		int cellCount = this.row.getPhysicalNumberOfCells();
		
		int i = 0;
		for (i = 0; i < cellCount; ++i) {
			if(this.row.getCell(i).getStringCellValue().trim().equals(key.trim())) {
				cellValue = i;
				break;
			}
		}
		
		for (i = 0; i < cellCount; ++i) {
			this.row = this.sheet.getRow(i);
			if(this.row.getCell(cellValue).getStringCellValue().trim().equals(value.trim())) {
				rowValue = i;
				break;
			}
		}
		
		for (i = 0; i < cellCount; ++i) {
			this.rowheader = this.sheet.getRow(0);
			String keyName = this.rowheader.getCell(i).getStringCellValue().trim();
			this.row = this.sheet.getRow(rowValue);
			String valueName = null;
			try {
				if(this.row.getCell(i).getStringCellValue().trim().startsWith("#")) {
					valueName  = this.row.getCell(i).getStringCellValue().substring(1);
				}else {
					valueName  = this.row.getCell(i).getStringCellValue().trim();
				}
				data.put(keyName, valueName);
			}catch (Exception var1) {
				log.info("Exception - " + var1);
			}
		}
		return data;
	}
	
	public String readDataForJsonValidations(String customer, String expectedVal) {
		
		String path = null;
		int cellValue = 0;
		//int rowValue = false;
		String ev = "";
		sheet = (XSSFSheet)this.context.getVar("sheet");
		int rowCount = sheet.getPhysicalNumberOfRows();
		for(int i = 0; i < rowCount; ++i) {
			this.row = sheet.getRow(i);
			/*if(this.row.getCell(1).getCellType() == 0) {
				ev = String.valueOf(Double.valueOf(this.row.getCell(1).getNumericCellValue()).intValue());
			}else {
				try {
					ev = this.row.getCell(1).getStringCellValue();
				} catch(Exception var3) {
					logger.info("Exception - " + var3);
				}
			}*/
			//if(this.row.getCell(cellValue).getStringCellValue().trim().equals(customer.trim()) && ev.trim().equals(expectedVal.trim())) {
			if(this.row.getCell(cellValue).getStringCellValue().trim().equals(customer.trim())) {
				path = this.row.getCell(1).getStringCellValue().trim();	
			}
		}
		return path;
	}

	public void jsonRequestExcel(String customer, String testCaseID, String sheet, String template) {
		this.connectionExcel(sheet);
		Map<String, String> testData = this.readData("TestCaseid",testCaseID);
		this.context.setVar("testData", testData);
		log.info(testData + System.lineSeparator());
		this.restServices.restRequest(template);
	}
	
	public void xmlRequestExcel(String customer, String testCaseID, String sheet, String template) {
		this.connectionExcel(sheet);
		Map<String, String> testData = this.readData("TestCaseid",testCaseID);
		this.context.setVar("testData", testData);
		log.info(testData + System.lineSeparator());
		this.soapServices.prepareXmlRequest(template);
	}

}
