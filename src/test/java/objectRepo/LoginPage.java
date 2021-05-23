package objectRepo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	private WebDriver lpDriver;
	
	public LoginPage(WebDriver rDriver) {
		lpDriver = rDriver;
		PageFactory.initElements(lpDriver, this);
			
	}
	
	By txtEmail = By.id("Email");
	By txtPassword = By.id("Password");
	By btnLogin = By.xpath("//button[contains(text(),'Log in')]");
	By lnkLogout = By.linkText("Logout");
	
	 public void setUserName(String uname) {
		 lpDriver.findElement(txtEmail).clear();
		 lpDriver.findElement(txtEmail).sendKeys(uname);

	    }

	    public void setPassword(String pwd) {
	    	lpDriver.findElement(txtPassword).clear();
	    	lpDriver.findElement(txtPassword).sendKeys(pwd);
	    }

	    public void clickLogin() {
	    	lpDriver.findElement(btnLogin).click();
	    }

	    public void clickLogout() {
	    	lpDriver.findElement(lnkLogout).click();
	    }

}
