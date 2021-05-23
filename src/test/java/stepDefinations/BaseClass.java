package stepDefinations;

import java.util.Properties;
import org.apache.log4j.Logger;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;

import objectRepo.AddcustomerPage;
import objectRepo.LoginPage;
import objectRepo.SearchCustomerPage;

public class BaseClass {
	
	public WebDriver driver;
    public LoginPage lp;
    public AddcustomerPage addCust;
    public SearchCustomerPage searchCust;
    public static Logger logger;
    Properties configProp;


    //Created for generating random string for Unique email
    public static String randomestring() {
        String generatedString1 = RandomStringUtils.randomAlphabetic(5);
        return (generatedString1);
    }

}
