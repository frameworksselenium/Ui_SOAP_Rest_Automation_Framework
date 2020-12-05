package com.open.hotel.stepdefinitions;

import com.open.abddf.context.TestContext;
import com.open.hotel.pages.Login;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSetpDefinition {

	private TestContext context;
	public Login login = null;

	public LoginSetpDefinition(TestContext context){
		this.context = context;
	}

	@Given("Open Browser")
	public void OpenBrowser() {

		this.context.OpenBrowser();
	}

	@Given("User is able Launch the hotel application using {string}")
	public void user_is_able_Launch_the_hotel_application_using(String arg1) throws InterruptedException {
		login = new Login(this.context);
		login.lauchApplication(arg1);
	}

	@When("User enters the {string} and {string} and click on login button")
	public void user_enters_the_and(String arg1, String arg2) throws Exception {
		login.login(arg1, arg2);
	}

	@Then("LogOut application")
	public void logout_application() throws Exception {
		login.LogOut();
	}

}