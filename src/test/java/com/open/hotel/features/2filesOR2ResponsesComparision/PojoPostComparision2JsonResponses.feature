Feature: Compare 2 Json files

  @Compare2files4
  Scenario: 109:Compare 2 Json or xml or yaml files which are stored in disk
    When I submit the JSON "GET" request with endpoint "/public-api/users" for "GoRest" service and save response "response1"
    When I submit the JSON "GET" request with endpoint "/public-api/users" for "GoRest" service and save response "response2"
    And compare 2 "json" responses - "response1" and "response2" using pojo



