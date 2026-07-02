package com.selenium.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DockerWithChrome {
    private static final Logger logger = LoggerFactory.getLogger(DockerWithChrome.class);
    private static WebDriver driver;

    public static void main(String[] args) throws MalformedURLException {
        try {
            // Use ChromeOptions instead of DesiredCapabilities (Selenium 4)
            ChromeOptions options = new ChromeOptions();
            
            URL url = new URL("http://chrome:4444/wd/hub");
            driver = new RemoteWebDriver(url, options);
            logger.info("Connected to Remote Chrome");

            driver.navigate().to("https://www.google.com");

            // Verify page title
            String title = driver.getTitle();
            logger.info("Page title: " + title);
            assert title.contains("Google") : 
                "Page title should contain 'Google'";
            
            logger.info("Test passed!");
        } catch (Exception e) {
            logger.error("Test failed", e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}