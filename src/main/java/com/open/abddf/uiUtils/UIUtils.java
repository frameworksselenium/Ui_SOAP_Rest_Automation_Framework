package com.open.abddf.uiUtils;

import com.open.abddf.context.TestContext;
import com.open.abddf.logger.LoggerClass;
import com.open.abddf.config.Config;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UIUtils {

    org.apache.log4j.Logger log;
    public TestContext context;
    public UIUtils(TestContext context){
        this.context = context;
        log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), this.context.getVar("testCaseName").toString());
    }
    public UIUtils(){

    }
    public void type(WebElement element, String value, String elementName, String page){
        try{
            boolean elementClickable = WaitUntilClickable(element, Integer.valueOf(Config.properties.getProperty("LONGWAIT")));
            highlightElement(element);
            MouseMoveToElement(element);
            //scrollToElement(element);
            element.sendKeys(value);
            log.info("Thread ID:'" + Thread.currentThread().getId() + "' 'PASS' Entered value '" + value + "' in '" + elementName + "' text box");
            //HtmlLog.insertResult(this.context.getVar("testCaseID").toString(), "ThreadID: " + String.valueOf(Thread.currentThread().getId()), "Enter value '" + value + "' in '" + elementName + "' text box", "Entered value '" + value + "' in '" + elementName + "' text box","PASS");
        }catch(Exception e){
            //HtmlLog.insertResult(this.context.getVar("testCaseID").toString(), "ThreadID: " + String.valueOf(Thread.currentThread().getId()), "Enter value '" + value + "' in '" + elementName + "' text box", "Not Entered value '" + value + "' in '" + elementName + "' text box","FAIL");
            log.info("Thread ID:'" + Thread.currentThread().getId() + "' 'FAIL' " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void clickElement(WebElement element, String elementName, String page){
        try{
            boolean elementClickable = WaitUntilClickable(element, Integer.valueOf(Config.properties.getProperty("LONGWAIT")));
            highlightElement(element);
            MouseMoveToElement(element);
            //scrollToElement(element);
            element.click();
            log.info("Thread ID:'" + Thread.currentThread().getId() + "' 'PASS' Clicked on '" + elementName + "' button");
            //HtmlLog.insertResult(this.context.getVar("testCaseID").toString(), "ThreadID: " + String.valueOf(Thread.currentThread().getId()), "Click on '" + elementName + "' button", "Clicked on '" + elementName + "' button", "PASS");
        }catch(Exception e){
            //HtmlLog.insertResult(this.context.getVar("testCaseID").toString(), "ThreadID: " + String.valueOf(Thread.currentThread().getId()), "Click on '" + elementName + "' button", "not Clicked on '" + elementName + "' button", "FAIL");
            log.info("Thred ID:'" + Thread.currentThread().getId() + "' 'FAIL' " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean WaitUntilClickable(WebElement element, int iWaitTime) throws Exception {

        boolean bFlag = false;
        WebDriverWait wait = new WebDriverWait(this.context.driver(), iWaitTime);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            e.printStackTrace();
            bFlag = false;
        }
        return bFlag;

    }

    public void WaitUntilElementInvisible(WebElement element, int timeOut) {
        WebDriverWait wait = new WebDriverWait(this.context.driver(), timeOut);
            wait.until(ExpectedConditions.invisibilityOf(element));
     }

    public void MouseMoveToElement(WebElement element) throws InterruptedException {
        Actions action = new Actions(this.context.driver());
        Thread.sleep(500);
        action.moveToElement(element).build().perform();
    }

    public  void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor)this.context.driver();
        js.executeScript("arguments[0].scrollIntoView();)", element);
    }

    public void highlightElement(WebElement element) throws Exception {
            String attributevalue = "border:10px solid green;";
            JavascriptExecutor executor = (JavascriptExecutor) this.context.driver();
            String getattrib = element.getAttribute("style");
            executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, attributevalue);
            Thread.sleep(100);
            executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, getattrib);
    }

}
