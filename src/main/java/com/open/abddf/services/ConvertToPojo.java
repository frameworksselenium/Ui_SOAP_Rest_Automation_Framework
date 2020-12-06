package com.open.abddf.services;

import com.google.gson.Gson;
import com.open.abddf.context.TestContext;
import com.open.abddf.logger.LoggerClass;
import java.io.*;


public final class ConvertToPojo {

    TestContext context;
    org.apache.log4j.Logger log;

    public ConvertToPojo(TestContext context) {
        this.context = context;
        log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), this.context.getVar("testCaseName").toString());
    }

    public static <T> T deserialize(String filePath, Class<T> schema){
        try{
            Gson gson = new Gson();
            if(filePath.substring(filePath.length() - 5).equalsIgnoreCase(".json")){
                FileReader reader = new FileReader(filePath);
                return gson.fromJson(reader, schema);
            }else{
                return gson.fromJson(filePath, schema);
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}