Feature: send json request and get response

  @RestApiCSVGetRequestWithRestAssured
  Scenario: 105:send json request for scenario 1 and get response CSV
    When I submit the JSON "GET" request with endpoint "/public-api/users/1586" for "GoRest" service with RestAssured
    Then Validate "praveen" from "name" JSON response - json path "$.data.name"
    Then Validate "praveen@abc.com" from "email" JSON response - json path "data.email"
    Then Validate "Male" from "gender" JSON response - json path "data.gender"
    Then Validate "Active" from "status" JSON response - json path "data.status"

