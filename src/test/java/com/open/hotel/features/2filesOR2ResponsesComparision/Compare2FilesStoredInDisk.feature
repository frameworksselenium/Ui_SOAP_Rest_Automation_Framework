Feature: end request to 2 end points and compare 2 responses

  @Compare2files
  Scenario: 110:Send request to 2 end points and compare 2 responses
    Given compare 2 "json" files which stored in disk - file1 "data1.json" and file2 "data2.json"
