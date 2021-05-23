package stepDefinations;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import objectRepo.AddcustomerPage;
import objectRepo.LoginPage;
import objectRepo.SearchCustomerPage;

public class Steps extends BaseClass{
	

	
	@Before
    public void setup() throws IOException
    {

        //Logger steps
		
		  logger= Logger.getLogger("nopcommerce");
		  PropertyConfigurator.configure("Log4j.properties");
		  logger.setLevel(Level.DEBUG);
		 
        //Logger steps done

        //Loading Config.properties file steps
        configProp=new Properties();
        FileInputStream configPropfile = new FileInputStream("config.properties");
        configProp.load(configPropfile);
        //Loading Config.properties file is done


        String br=configProp.getProperty("browser");

        if (br.equals("firefox")) {
           
            driver = new FirefoxDriver();
        }

        else if (br.equals("chrome")) {
        	WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        else if (br.equals("ie")) {
            
            driver = new InternetExplorerDriver();
        }

    }
	
	@After(order=0)
	public void quitBrowser() {
		driver.quit();
	}
	
	@After(order =1)
	public void tearDown(Scenario scenario) {
		//if (scenario.isFailed())	{
			//take screenshots
			String screenShotName = scenario.getName().replaceAll(" ", "_");
			
			byte[] sourcePath = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(sourcePath, "image/png", screenShotName);
		//}
	}
	
	
	@Given("User Launch Chrome browser")
	public void user_launch_chrome_browser() {
		lp = new LoginPage(driver);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
	}

	@When("User opens URL {string}")
	public void user_opens_url(String url) {
		driver.get(url);
	    
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_email_as_and_password_as(String email, String password) {
		lp.setUserName(email);
		lp.setPassword(password);
		

	}

	@When("Click on Login")
	public void click_on_login() throws InterruptedException {
		lp.clickLogin();
		Thread.sleep(5000);
	}

	@Then("Page Title should be {string}")
	public void page_title_should_be(String expTitle) {
		
		if (driver.getPageSource().contains("Login was unsuccessful")) {
			driver.close();
			Assert.assertTrue(false);
		}
		else {
			Assert.assertEquals(expTitle, driver.getTitle());
		}
		
	}

	@When("User click on Log out link")
	public void user_click_on_log_out_link() {
		lp.clickLogout();

	}

	@Then("close browser")
	public void close_browser() {
		driver.quit();
	}
	
	@Then("User can view Dashboad")
    public void user_can_view_Dashboad() {

        logger.info("********* Adding Customer Scenario started  ***************");
        addCust=new AddcustomerPage(driver);
        logger.info("********* Dashboard Display validation ***************");
        Assert.assertEquals("Dashboard / nopCommerce administration",addCust.getPageTitle());

    }

    @When("User click on customers Menu")
    public void user_click_on_customers_Menu() throws InterruptedException
    {
        Thread.sleep(3000);
        addCust.clickOnCustomersMenu();
    }

    @When("click on customers Menu Item")
    public void click_on_customers_Menu_Item() throws InterruptedException
    {
        Thread.sleep(2000);
        addCust.clickOnCustomersMenuItem();
    }

    @When("click on Add new button")
    public void click_on_Add_new_button() {
        addCust.clickOnAddnew();
    }

    @Then("User can view Add new customer page")
    public void user_can_view_Add_new_customer_page() {
        Assert.assertEquals("Add a new customer / nopCommerce administration", addCust.getPageTitle());
    }

    @When("User enter customer info")
    public void user_enter_customer_info() throws InterruptedException
    {
        logger.info("********* Providing Customer details ***************");
        String email = randomestring() + "@gmail.com";
        addCust.setEmail(email);
        addCust.setPassword("test123");
        // Registered - default
        // The customer cannot be in both 'Guests' and 'Registered' customer roles
        // Add the customer to 'Guests' or 'Registered' customer role
        addCust.setCustomerRoles("Guest");
        Thread.sleep(3000);

        addCust.setManagerOfVendor("Vendor 2");
        addCust.setGender("Male");
        addCust.setFirstName("Pavan");
        addCust.setLastName("Kumar");
        addCust.setDob("7/05/1985"); // Format: D/MM/YYY
        addCust.setCompanyName("busyQA");
        addCust.setAdminContent("This is for testing.........");
    }

    @When("click on Save button")
    public void click_on_Save_button() {
    addCust.clickOnSave();
    }

    @Then("User can view confirmation message {string}")
    public void user_can_view_confirmation_message(String string) {
        logger.info("********* Add customer validation ***************");
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
                .contains("The new customer has been added successfully"));
    }

    //Searching customers using EMail ID ...................................

    @When("Enter customer EMail")
    public void enter_customer_EMail() {
        logger.info("********* Search Customer by Email ID Scenario started ***************");
        searchCust=new SearchCustomerPage(driver);
        searchCust.setEmail("victoria_victoria@nopCommerce.com");
    }

    @When("Click on search button")
    public void click_on_search_button() throws InterruptedException
    {
        searchCust.clickSearch();
        Thread.sleep(3000);
    }

    @Then("User should found Email in the Search table")
    public void user_should_found_Email_in_the_Search_table() {
        logger.info("********* Search customer by email validation ***************");
        boolean status=searchCust.searchCustomerByEmail("victoria_victoria@nopCommerce.com");
        Assert.assertEquals(true, status);
    }

    //Searching customers using Name ...................................
    @When("Enter customer FirstName")
    public void enter_customer_FirstName() {
        logger.info("********* Seqarch custoemr by Name Scenario started ***************");
        searchCust=new SearchCustomerPage(driver);
        searchCust.setFirstName("Victoria");
    }

    @When("Enter customer LastName")
    public void enter_customer_LastName() {
        logger.info("********* Providing customer name ***************");

        searchCust.setLastName("Terces");
    }

    @Then("User should found Name in the Search table")
    public void user_should_found_Name_in_the_Search_table() {
        logger.info("********* Search customer by name validation ***************");

        boolean status=searchCust.searchCustomerByName("Victoria Terces");
        Assert.assertEquals(true, status);
    }

}
