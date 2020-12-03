package com.open.abddf.config;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Config {
    public static Properties properties = null;

    static {
        properties = Config.init();

    }
    public static Properties init(){

        properties = new Properties();
        try{
            File directory = new File (".");
            String rootFolderName = directory.getCanonicalPath();
            String configFilePath = rootFolderName + "\\src\\test\\resources\\config";
            FileInputStream fis = new FileInputStream( configFilePath + "\\Sys.properties");
            properties.load(fis);
            fis.close();
            properties.setProperty("resultFolder", rootFolderName + "/target/Reports");
            properties.setProperty("resultFolderName", rootFolderName + "/target/Reports/Execution_" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return properties;

    }

    public static void createFolder(String folderPath){
        File objDir=new File(folderPath);
        if(!objDir.exists()) {
            objDir.mkdir();
        }
    }

}
