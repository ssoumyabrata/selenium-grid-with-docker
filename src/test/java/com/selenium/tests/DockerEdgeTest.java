package com.selenium.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.selenium.tests.util.ScreenshotUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Selenium test class for Docker Edge container
 */
@Listeners({com.selenium.tests.listener.AllureListener.class})
public class DockerEdgeTest {
    private static final Logger logger = LoggerFactory.getLogger(DockerEdgeTest.class);
    private WebDriver driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        logger.info("Setting up RemoteWebDriver for Docker Chrome");
        try {

            EdgeOptions options = new EdgeOptions();

            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
            options.setExperimentalOption("useAutomationExtension", false);

            // Add user agent
            options.addArguments(
                    "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

            // Additional stealth options
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
            options.addArguments("--window-size=1920,1080");

            // Connect to Selenium Grid running in Docker
            URL url = new URL("http://selenium-hub:4444/wd/hub");
            driver = new RemoteWebDriver(url, options);
            logger.info("Connected to Remote Edge via Selenium Grid");
        } catch (Exception e) {
            logger.error("Failed to initialize RemoteWebDriver", e);
            throw new RuntimeException("Failed to initialize RemoteWebDriver", e);
        }
    }

    @Test
    public void testGoogleSearchDocker() {
        try {
            logger.info("Starting Google search test on Docker Edge");
            // driver = DriverManager.getDriver();

            // Navigate to Google
            driver.navigate().to("https://www.google.com");
            logger.info("Navigated to Google.com");
            ScreenshotUtil.takeScreenshot(driver, "02_search_entered");

            // Verify page title
            String title = driver.getTitle();
            logger.info("Page title: " + title);
            assert title.contains("Google") : "Page title should contain 'Google'";

            // Find search box and search
            var searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("Selenium WebDriver");
            ScreenshotUtil.takeScreenshot(driver, "02_search_entered");
            searchBox.submit();
            ScreenshotUtil.takeScreenshot(driver, "03_search_results");
            logger.info("Performed search for 'Selenium WebDriver'");

            // Wait and verify results
            Thread.sleep(2000);

            String resultPageTitle = driver.getTitle();
            logger.info("Result page title: " + resultPageTitle);
            assert resultPageTitle.contains("Selenium WebDriver") : "Results should contain 'Selenium WebDriver'";

            logger.info("Google search test completed successfully");
        } catch (Exception e) {
            logger.error("Test failed: ", e);
            throw new RuntimeException("Test execution failed", e);
        }
    }

    @Test
    public void testPageNavigationDocker() {
        try {
            logger.info("Starting page navigation test on Docker Edge");

            // Navigate to a website
            driver.navigate().to("https://www.example.com");
            logger.info("Navigated to example.com");

            // Verify page title
            String title = driver.getTitle();
            logger.info("Page title: " + title);
            assert title.contains("Example") : "Page title should contain 'Example'";

            logger.info("Page navigation test completed successfully");
        } catch (Exception e) {
            logger.error("Test failed: ", e);
            throw new RuntimeException("Test execution failed", e);
        }
    }

    @AfterClass
    public void tearDown() {
        logger.info("Tearing down RemoteWebDriver");
        if (driver != null) {
            try {
                driver.quit();
                logger.info("RemoteWebDriver closed successfully");
            } catch (Exception e) {
                logger.error("Failed to close RemoteWebDriver", e);
            }
        }
    }
}
