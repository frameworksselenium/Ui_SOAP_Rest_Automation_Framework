package com.open.abddf.flatMap;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.open.abddf.context.TestContext;
import com.open.abddf.logger.LoggerClass;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.XML;
import org.yaml.snakeyaml.Yaml;
import pojo.request1.Response1Pojo;
import pojo.request2.Response2Pojo;

import java.io.*;
import java.lang.reflect.Type;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class FlatMapUtil {

    TestContext context;
    org.apache.log4j.Logger log;

    public FlatMapUtil(TestContext context) {
        this.context = context;
        log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), this.context.getVar("testCaseName").toString());
        //throw new AssertionError("No instances for you!");
    }

    public static Map<String, Object> flatten(Map<String, Object> map) {
        return map.entrySet()
                .stream()
                .flatMap(FlatMapUtil::flatten)
                .collect(LinkedHashMap::new, (m, e) -> m.put("/" + e.getKey(), e.getValue()), LinkedHashMap::putAll);
    }

    private static Stream<Entry<String, Object>> flatten(Entry<String, Object> entry) {

        if (entry == null) {
            return Stream.empty();
        }

        if (entry.getValue() instanceof Map<?, ?>) {
            Map<?, ?> properties = (Map<?, ?>) entry.getValue();
            return properties.entrySet()
                    .stream()
                    .flatMap(e -> flatten(new SimpleEntry<>(entry.getKey() + "/" + e.getKey(), e.getValue())));
        }

        if (entry.getValue() instanceof List<?>) {
            List<?> list = (List<?>) entry.getValue();
            return IntStream.range(0, list.size())
                    .mapToObj(i -> new SimpleEntry<String, Object>(entry.getKey() + "/" + i, list.get(i)))
                    .flatMap(FlatMapUtil::flatten);
        }
        return Stream.of(entry);
    }

    public static void compare2Files(String leftJson, String rightJson) throws IOException {

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();

        Map<String, Object> leftMap = gson.fromJson(leftJson, type);
        Map<String, Object> rightMap = gson.fromJson(rightJson, type);

        Map<String, Object> leftFlatMap = FlatMapUtil.flatten(leftMap);
        Map<String, Object> rightFlatMap = FlatMapUtil.flatten(rightMap);

        MapDifference<String, Object> difference = Maps.difference(leftFlatMap, rightFlatMap);

        System.out.println("Entries only on left\n--------------------------");
        difference.entriesOnlyOnLeft().forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println("\n\nEntries only on right\n--------------------------");
        difference.entriesOnlyOnRight().forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println("\n\nEntries differing\n--------------------------");
        difference.entriesDiffering().forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println("\n\nEntries in common\n--------------------------");
        difference.entriesInCommon().forEach((key, value) -> System.out.println(key + ": " + value));

    }

    public static void compareFiles(String fileType, String leftFile, String rightFile){
        String expected = getJson(leftFile,fileType);
        String actual = getJson(rightFile, fileType);
        try{
            compare2Files(expected, actual);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void compareFiles(String leftFile, String rightFile){
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\templates\\comparefiles";
        leftFile = filePath + "//" + leftFile;
        rightFile = filePath + "//" + rightFile;

        String expected = getJson(leftFile);
        String actual = getJson(rightFile);
        try{
            compare2Files(expected, actual);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String getJson(String filePath, String fileType){
        String stringtext = null;
        if(fileType.contains("json")){
            //stringtext = readJsonFileAsString(filePath);
            return filePath;
        }else if(fileType.contains("yaml")){
            stringtext = convertYamltoJson(filePath);
            return stringtext;
        }else if(fileType.contains("xml")){
            stringtext = convertXMLtoJson1(filePath);
            return stringtext;
        }else{
            throw new RuntimeException("Unsupported types.");
        }
    }

    public static String getJson(String filePath){
        String stringtext = null;
        if(isJson(filePath)){
            stringtext = readJsonFileAsString(filePath);
            return stringtext;
        }else if(isYaml(filePath)){
            stringtext = convertYamltoJson(filePath);
            return stringtext;
        }else if(isXML(filePath)){
            stringtext = convertXMLtoJson(filePath);
            return stringtext;
        }else{
            throw new RuntimeException("Unsupported types.");
        }
    }

    public static String convertYamltoJson(String fileName){
        try{
            InputStream input = new FileInputStream(new File(fileName));
            Yaml yaml = new Yaml();
            @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>)yaml.load(input);
            org.json.JSONObject jsonObject = new org.json.JSONObject(map);
            try{
                return jsonObject.toString(4);
            }catch (JSONException e){
                throw new RuntimeException(e.getMessage());
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String convertXMLtoJson1(String fileName){
        org.json.JSONObject soapDefinitionObject = null;
        try{
            //InputStream input = new FileInputStream(new File(fileName));
            try{
                soapDefinitionObject = XML.toJSONObject(fileName);
            }catch (JSONException ee){
                throw new RuntimeException(ee.getMessage());
           // }catch (IOException eee){
             //   throw new RuntimeException(eee.getMessage());
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return  soapDefinitionObject.toString();
    }

    public static String convertXMLtoJson(String fileName){
        org.json.JSONObject soapDefinitionObject = null;
        try{
            InputStream input = new FileInputStream(new File(fileName));
            try{
                soapDefinitionObject = XML.toJSONObject(IOUtils.toString(input));
            }catch (JSONException ee){
                throw new RuntimeException(ee.getMessage());
            }catch (IOException eee){
                throw new RuntimeException(eee.getMessage());
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException(e.getMessage());
        }
        return  soapDefinitionObject.toString();
    }

    public static String readJsonFileAsString(String filePath){
        String json = null;
        try{
            json = FileUtils.readFileToString(new File(filePath));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return json;
    }

    public static boolean isJson(String filePath){
        if(getFileExtension(filePath).equalsIgnoreCase("json")){
            return true;
        }
        return false;
    }

    public static boolean isYaml(String filePath){
        if(getFileExtension(filePath).equalsIgnoreCase("yaml")){
            return true;
        }
        return false;
    }

    public static boolean isXML(String filePath){
        if(getFileExtension(filePath).equalsIgnoreCase("xml")){
            return true;
        }
        return false;
    }
    public static String getFileExtension(String filePath){
        return Files.getFileExtension(filePath).trim();
    }

}