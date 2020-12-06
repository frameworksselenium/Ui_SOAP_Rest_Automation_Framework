package com.open.abddf.services;

import com.open.abddf.context.TestContext;
import com.open.abddf.logger.LoggerClass;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class XMLUtilitySupport {

	org.apache.log4j.Logger log;
	public TestContext context;
	public XMLUtilitySupport(TestContext context) {
		this.context = context;
		log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), this.context.getVar("testCaseName").toString());
	}
	public Object getValueFromResponse(String xmlStr, String xpathExpression) {
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource inputSource = new InputSource(new java.io.StringReader(xmlStr));
			//Document doc = builder.parse(new InputSource(new StringRader(xmlStr)));
			Document doc = builder.parse(inputSource);
			
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			return xPath.evaluate(xpathExpression, doc, XPathConstants.NODESET);
		} catch (Exception val5) {	
			log.info("Exception - " + val5);
		}
		return "";
	}
}
