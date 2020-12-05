package com.open.abddf.utilities;

import com.open.abddf.context.TestContext;
import com.open.abddf.logger.LoggerClass;
import org.testng.Assert;

public class AssertUtl {

    org.apache.log4j.Logger log;
    TestContext context;

    public AssertUtl(TestContext context){
        this.context = context;
        log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), this.context.getVar("testCaseName").toString());
    }
    public void assertEquals(String expected, String Actual){
        if(expected.trim().equalsIgnoreCase(Actual.trim())){
        }
        log.info("Exected:-" + expected + ", Actual:-" + Actual);
        Assert.assertEquals(expected, Actual);
    }

    public void assertEquals(boolean expected, boolean Actual){
        if(expected == Actual){
        }
        log.info("Exected:-" + expected + ", Actual:-" + Actual);
        Assert.assertEquals(expected, Actual);
    }

    public void assertEquals(int expected, int Actual){
        if(expected == Actual){
        }
        log.info( "Exected:-" + expected + ", Actual:-" + Actual);
        Assert.assertEquals(expected, Actual);
    }

}