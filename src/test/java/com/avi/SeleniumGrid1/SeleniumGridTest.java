package com.avi.SeleniumGrid1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.ObjectInputFilter.Config;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class SeleniumGridTest {
    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        // Define the URL of the Selenium Grid Hub
    		URL hubUrl = new URL(System.getenv("SELENIUM_GRID_URL"));

            // Define desired capabilities for the browser
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("chrome");

            // Initialize the RemoteWebDriver with the Hub URL and capabilities
            WebDriver driver = new RemoteWebDriver(hubUrl, capabilities);
            
    	
        

        // Your test code here
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
        
        Thread.sleep(10000); // Buying enough time for me to check on which port it's running

        // Close the browser
        driver.quit();
    }
}
