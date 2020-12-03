package com.open.abddf.logger;

import com.open.abddf.config.Config;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

public  class LoggerClass {

    public static Logger getThreadLogger(String str, String tc){
        if(tc.contains(("_"))) {
            String tcs[]= tc.split("_");
            tc = tcs[0];
        }
        Logger log = Logger.getLogger(str + "_" + tc);
        Properties props = new Properties();
        Long threadID = Thread.currentThread().getId();
        props.setProperty("log4j.appender.file","org.apache.log4j.DailyRollingFileAppender");
        props.setProperty("log4j.appender.stdout","org.apache.log4j.ConsoleAppender");
        props.setProperty("log4j.appender.file.DatePattern","'.'yyyy-MM-dd");
        props.setProperty("log4j.appender.file.layout","org.apache.log4j.PatternLayout");
        props.setProperty("log4j.appender.file.File", Config.properties.getProperty("resultFolderName") +"//Log//" + str + "_" + tc + "_" + threadID + ".log");
        props.setProperty("log4j.appender.file.layout.ConversionPattern","%p - %d{dd-MM-yyyy HH:mm:ss} - %C{1} - %M - %m%n");
        props.setProperty("log4j.appender.stdout.layout","org.apache.log4j.PatternLayout");
        props.setProperty("log4j.appender.stdout.layout.ConversionPattern","%5p %d{h:mm a}  %M - %m%n");
        props.setProperty("log4j.logger." + str + "_" + tc,"DEBUG, file, stdout");
        PropertyConfigurator.configure(props);
        return log;
    }
}
