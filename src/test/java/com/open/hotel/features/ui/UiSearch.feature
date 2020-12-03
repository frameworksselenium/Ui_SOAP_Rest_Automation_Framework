Feature: search room in hotel application

  @SmokeTestui
  Scenario: 202:search room
    Given Open Browser
    Then User is able Launch the hotel application using "http://adactin.com/HotelApp/index.php"
    When User enters the "kmanubolu" and "USi+QGYRzFE7NU9QEw2rZg==" and click on login button
    #Then User naviaged to home page
    And user enters the required information in search hotel page
      | UILables          | Values      |
      | Location          | Sydney      |
      | Hotels            | Hotel Creek |
      | Room Type         | Standard    |
      | Number of Rooms   | 1 - One     |
      | Check In Date     | 17/07/2020  |
      | Check Out Date    | 18/07/2020  |
      | Adults per Room   | 1 - One     |
      | Children per Room | 2 - Two     |
    And user clicks the search button
    And LogOut application