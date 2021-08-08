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
 public static WebDriver createWebDriverInstance(String strDevice) {
        WebDriver driver = null;

        try {

            ChromeOptions chromeOptions;
            FirefoxOptions firefoxOptions;
            URL testGridUrl = null;
            String strExecutionPlatform = System.getProperty("executionPlatform").trim().toUpperCase();
            //LOCAL_CHROME, LOCAL_FIREFOX, AWS_CHROME, AWS_FIREFOX, AWS_DEVICEFARM_CHROME, AWS_DEVICEFARM_FIREFOX
            Map<String, String> mobileEmulation = new HashMap<>();
            //Nexus 7, Galaxy S5, iPad, Pixel 2
            if (!strDevice.isEmpty() && !strDevice.equalsIgnoreCase("Web")) {
                mobileEmulation.put("deviceName", strDevice);
            }

            switch (strExecutionPlatform) {
                case "LOCAL_CHROME":
                    chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "LOCAL_FIREFOX":
                    firefoxOptions = new FirefoxOptions();
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case "AWS_CHROME":
                    chromeOptions = new ChromeOptions();
                    chromeOptions.setHeadless(true);
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("enable-automation");
                    chromeOptions.addArguments("--disable-infobars");
                    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                    chromeOptions.setBinary(readData().getProperty("AWS_CHROME_BROWSER_PATH").trim());
//                    WebDriverManager.chromedriver().setup();
                    System.setProperty("webdriver.chrome.driver", readData().getProperty("AWS_CHROME_DRIVER_PATH").trim());
                    driver = new ChromeDriver(chromeOptions);
                    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                    break;
                case "AWS_FIREFOX":
                    firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setHeadless(true);
//                    System.setProperty("webdriver.gecko.driver", readData().getProperty("AWS_FIREFOX_DRIVER_PATH").trim());
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case "AWS_DEVICEFARM_CHROME":
                    testGridUrl = getTestGridUrl();
                    DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
                    chromeOptions = new ChromeOptions();
                    //                    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                    desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    driver = new RemoteWebDriver(testGridUrl, desiredCapabilities);
                    break;
                case "AWS_DEVICEFARM_FIREFOX":
                    testGridUrl = getTestGridUrl();
                    driver = new RemoteWebDriver(testGridUrl, DesiredCapabilities.firefox());
                    ExtentCucumberAdapter.addTestStepLog(strExecutionPlatform + " Driver Creation Completed");
                    break;
                case "AWS_FARGATE":
                    DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
                    ChromeOptions chromeOpt = new ChromeOptions();
                    chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOpt);
                    driver = new RemoteWebDriver(new URL("http://3.19.74.252:4444/wd/hub"), chromeCapabilities);
                    ExtentCucumberAdapter.addTestStepLog(strExecutionPlatform + " Driver Creation Completed");
                    break;
                default:
                    ExtentCucumberAdapter.addTestStepLog("ExecutionPlatform Platform must be set in settings file.");
            }
            ExtentCucumberAdapter.addTestStepLog(strExecutionPlatform + " Driver Creation Completed");
        } catch (
                Exception ex) {
            ex.printStackTrace();
        }
        return driver;
    }
	
public static Properties readData() {
        Properties objProp = new Properties();
        try {
            String env = System.getProperty("env");
            File file = new File("TestSettings.properties");
            FileInputStream fileInput = null;
            fileInput = new FileInputStream(file);
            objProp.load(fileInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objProp;
    }


    //Created for generating random string for Unique email
    public static String randomestring() {
        String generatedString1 = RandomStringUtils.randomAlphabetic(5);
        return (generatedString1);
    }

}
