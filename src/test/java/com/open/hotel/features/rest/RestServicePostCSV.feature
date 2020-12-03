Feature: send json request and get response

  @RestApiCSVPostRequest
  Scenario: 104:send json request for scenario 1 and get response CSV
    Given Customer "GoRest" Read data from "TestData.csv" with testcase ID "TC01" and Create JSON request using JSON template "CreateUser_Request"
    When I submit the JSON "POST" request with endpoint "/public-api/users/" for "GoRest" service
    Then Validate "Krishna" from "name" JSON response - json path "data.name"
    Then Validate "Krishna@gmail.com" from "email" JSON response - json path "data.email"
    Then Validate "Male" from "gender" JSON response - json path "data.gender"
    Then Validate "Active" from "status" JSON response - json path "data.status"

