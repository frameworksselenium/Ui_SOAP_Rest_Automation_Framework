Feature: send xml request and get response

  @SoapApiExcelPostRequest
  Scenario: 103:send xml request for scenario 1 and get response
    Given Customer "tempconvert" Create xml request using xml template "FharenheitToCensius_Request" and connect data sheet "Fahrenheit" with testcase ID "TC01"
    When I submit the xml request
    Then Validating tag "FahrenheitToCelsiusResponse" of "FahrenheitToCelsiusResult" as "51.1111111111111" from XML Response
