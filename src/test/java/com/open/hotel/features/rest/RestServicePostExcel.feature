@SmokeTest
Feature: Read data from excel and prepare json payload and send request and get response

  @RestApiExcelPostRequest
  Scenario: 106:Read data from excel and prepare json payload and send request and get response
    Given Customer "GoRest" Create JSON request using JSON template "CreateUser_Request" and connect Excel data sheet "CreateUser" with testcase ID "TC01"
    When I submit the JSON "POST" request with endpoint "/public-api/users/" for "GoRest" service
    Then Validate "Krishna103" from "name" JSON response - json path "$.data.name"
    Then Validate "Krishna103@gmail.com" from "email" JSON response - json path "data.email"
    Then Validate "Male" from "gender" JSON response - json path "data.gender"
    Then Validate "Active" from "status" JSON response - json path "data.status"
