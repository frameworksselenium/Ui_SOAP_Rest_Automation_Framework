Feature: Compare 2 Json files

  @Compare2files1
  Scenario: 109:Compare 2 Json or xml or yaml files which are stored in disk
    Given Customer "GoRest" Read data from "TestData.csv" with testcase ID "TC01" and Create JSON request using JSON template "CreateUser_Request"
    When I submit the JSON "POST" request with endpoint "/public-api/users/" for "GoRest" service and save response "response1"
    Given Customer "GoRest" Read data from "TestData.csv" with testcase ID "TC01" and Create JSON request using JSON template "CreateUser_Request"
    When I submit the JSON "POST" request with endpoint "/public-api/users/" for "GoRest" service and save response "response2"
    And compare 2 "json" responses - "response1" and "response2"



