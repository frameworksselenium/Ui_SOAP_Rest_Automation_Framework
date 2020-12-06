package com.open.hotel.pages;

import com.open.abddf.context.TestContext;
import com.open.abddf.security.Security;
import com.open.abddf.ui.UIUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Login extends UIUtils {

	TestContext context;
	WebDriver driver = null;
	String page = "Login";

	public Login(TestContext context){
		super(context);
		this.context = context;
		this.driver = this.context.driver();
		PageFactory.initElements(this.driver, this);
	}

	@FindBy(how =How.ID, using = "username")
	WebElement UserName;

	@FindBy(how =How.ID, using = "password")
	WebElement Password;

	@FindBy(how =How.ID, using = "login")
	WebElement Login;

	@FindBy(how =How.XPATH, using = "//*[contains(text(),'Search Hotel')]")
	WebElement SearchHotelText;

	@FindBy(how =How.XPATH, using = "//a[contains(text(),'Logout')]")
	WebElement LogOut;

	public void lauchApplication(String url)throws InterruptedException {
		//this.driver.manage().timeouts().implicitlyWait(55, TimeUnit.SECONDS);
		this.driver.get(url);
	}

	public void login(String userName, String password) {
		type(UserName, userName, "UserName", this.page);
		Security security = new Security();
		String decryptPasswordValue = security.decryptPassword(password);
		type(Password, decryptPasswordValue, "Password", this.page);
		clickElement(Login, "Login", this.page);
	}

	public void LogOut() {
		clickElement(LogOut, "LogOut", this.page);
	}

}