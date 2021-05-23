package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	

		
		public WebDriver driver;
		public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
		
		public WebDriver init_driver(String browser) {
			
			
			if(browser.contentEquals("Chrome")) {
				WebDriverManager.chromedriver().setup();
				tlDriver.set(new ChromeDriver());
			}
			
			else if (browser.contentEquals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				tlDriver.set(new FirefoxDriver());
			}
			else {
				System.out.println("Pass the correct driver input");
			}
			getDriver().manage().deleteAllCookies();
			getDriver().manage().window().maximize();
			return getDriver();
		}
		
		public static synchronized WebDriver getDriver() {
			return tlDriver.get();
		}

	

}
