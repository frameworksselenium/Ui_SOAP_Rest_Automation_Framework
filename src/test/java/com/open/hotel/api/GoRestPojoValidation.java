package com.open.hotel.api;

import com.open.abddf.context.TestContext;
import com.open.abddf.logger.LoggerClass;
import com.open.abddf.services.ConvertToPojo;
import io.cucumber.java.Scenario;
import pojo.request1.Response1Pojo;
import pojo.request2.Response2Pojo;

public final class GoRestPojoValidation {

    TestContext context;
    org.apache.log4j.Logger log;

    public GoRestPojoValidation(TestContext context) {
        this.context = context;
        log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), this.context.getVar("testCaseName").toString());
    }

    public void pojoComparision(String response1, String response2){
        try {
            Response1Pojo response1Pojo = ConvertToPojo.deserialize(response1, Response1Pojo.class);
            Response2Pojo response2Pojo = ConvertToPojo.deserialize(response2, Response2Pojo.class);
            //code
            String response1Code = response1Pojo.getCode().toString();
            String response2Code = response2Pojo.getCode().toString();
            assertPojoValues("code", response1Code, response2Code);
            //meta - total
            String response1Meta = response1Pojo.getMeta().getPagination().getTotal().toString();
            String response2Meta = response2Pojo.getMeta().getPagination().getTotal().toString();
            assertPojoValues("total", response1Meta, response2Meta);

            //meta - pages
            String response1pages = response1Pojo.getMeta().getPagination().getPages().toString();
            String response2pages = response2Pojo.getMeta().getPagination().getPages().toString();
            assertPojoValues("pages", response1pages, response2pages);

            //meta - Page
            String response1Page = response1Pojo.getMeta().getPagination().getPage().toString();
            String response2Page = response2Pojo.getMeta().getPagination().getPage().toString();
            assertPojoValues("Page", response1Page, response2Page);

            //meta - Limit
            String response1Limit = response1Pojo.getMeta().getPagination().getLimit().toString();
            String response2Limit = response2Pojo.getMeta().getPagination().getLimit().toString();
            assertPojoValues("Limit", response1Limit, response2Limit);
            int count;
            int count1;
            boolean recordExist =  false;
            for (count = 0; count<= response1Pojo.getData().size()-1; count++) {
                String response1Id = response1Pojo.getData().get(count).getId().toString();
                log.info("====================================Record ID : '" + response1Id + "' data validation started====================================");
                for (count1 = 0; count1<= response2Pojo.getData().size()-1; count1++) {
                    String response2Id = response2Pojo.getData().get(count1).getId().toString();
                    if(response1Id.equalsIgnoreCase(response2Id)){
                        recordExist =  true;
                        break;
                    }
                }
                if(!recordExist){
                    log.info("Response1 record not found in Response2 : ID : '" + response1Id + "'");
                }
                //Id
                String res1ID = response1Pojo.getData().get(count).getId().toString();
                String res2ID = response2Pojo.getData().get(count1).getId().toString();
                assertPojoValues("Id", res1ID, res2ID);

                //Name
                String response1Name = response1Pojo.getData().get(count).getName().toString();
                String response2Name = response2Pojo.getData().get(count1).getName().toString();
                assertPojoValues("Name", response1Name, response2Name);

                //Email
                String response1Email = response1Pojo.getData().get(count).getEmail().toString();
                String response2Email = response2Pojo.getData().get(count1).getEmail().toString();
                assertPojoValues("Email", response1Email, response2Email);

                //Gender
                String response1Gender = response1Pojo.getData().get(count).getGender().toString();
                String response2Gender = response2Pojo.getData().get(count1).getGender().toString();
                assertPojoValues("Gender", response1Gender, response2Gender);

                //Status
                String response1Status = response1Pojo.getData().get(count).getStatus().toString();
                String response2Status = response2Pojo.getData().get(count1).getStatus().toString();
                assertPojoValues("Status", response1Status, response2Status);

                //createdat
                String response1createdat = response1Pojo.getData().get(count).getCreatedAt().toString();
                String response2createdat = response2Pojo.getData().get(count1).getCreatedAt().toString();
                assertPojoValues("createdat", response1createdat, response2createdat);

                //updatedat
                String response1updatedat = response1Pojo.getData().get(count).getUpdatedAt().toString();
                String response2updatedat = response2Pojo.getData().get(count1).getUpdatedAt().toString();
                assertPojoValues("updatedat", response1updatedat, response2updatedat);
                log.info("====================================Record ID : '" + response1Id + "' data validation ended====================================");

            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void assertPojoValues(String nodeName, String response1Value, String response2Value){
        Scenario scenario = (Scenario)this.context.getVar("scenario");
        if(response1Value.equalsIgnoreCase(response2Value)){
            String passString = "PASS:: Response1 Node Name : '" + nodeName + "' and Values :'" + response1Value + "' - Response2 Node Name : '" + nodeName + "' and Values :'" + response2Value + "'";
            scenario.write("Response : " + passString);
            log.info(passString);
        }else{
            String failString = "FAIL:: Response1 Node Name : '" + nodeName + "' and Values :'" + response1Value + "' - Response2 Node Name : '" + nodeName + "' and Values :'" + response2Value + "'";
            scenario.write("Response : " + failString);
            log.info(failString);
        }
    }
}