Feature: send xml request and get response

  @SOAPApiPostRequestCucumberParameterization
  Scenario: 109:send xml request for scenario 1 and get response
    Given Customer "tempconvert" Create XML request using XML template "FharenheitToCensius_Request"
      | NodeName          | Values      |
      | Fahrenheit              | 200      |
    When I submit the xml request
    Then Validating tag "FahrenheitToCelsiusResponse" of "FahrenheitToCelsiusResult" as "93.3333333333333" from XML Response
