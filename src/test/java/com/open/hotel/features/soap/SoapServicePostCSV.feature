Feature: send xml request and get response

  @SoapApiCSVPostRequest
  Scenario: 107:Read Data from CSV and send xml request and get response
    Given Customer "tempconvert" Read data from "Fahrenheit.csv" CSV file with testcase ID "TC01" and Create XML request using XML template "FharenheitToCensius_Request"
    When I submit the xml request
    Then Validating tag "FahrenheitToCelsiusResponse" of "FahrenheitToCelsiusResult" as "93.3333333333333" from XML Response
