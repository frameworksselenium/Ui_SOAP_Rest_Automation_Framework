Feature: login hotel application

  @SmokeTest
  Scenario: 101:login to the hotel application
    Given Open Browser
    Then User is able Launch the hotel application using "http://adactin.com/HotelApp/index.php"
    When User enters the "kmanubolu" and "USi+QGYRzFE7NU9QEw2rZg==" and click on login button
	And LogOut application
    
