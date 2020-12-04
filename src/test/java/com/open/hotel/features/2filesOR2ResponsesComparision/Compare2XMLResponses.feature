Feature: Compare 2 Json files

  @Compare2files2
  Scenario: 111:Compare 2 Json or xml or yaml files which are stored in disk
    Given Customer "tempconvert" Read data from "Fahrenheit.csv" CSV file with testcase ID "TC01" and Create XML request using XML template "FharenheitToCensius_Request"
    When I submit the "xml" request and save response "response1"
    Given Customer "tempconvert" Read data from "Fahrenheit.csv" CSV file with testcase ID "TC01" and Create XML request using XML template "FharenheitToCensius_Request"
    When I submit the "xml" request and save response "response2"
    And compare 2 "xml" responses - "response1" and "response2"



