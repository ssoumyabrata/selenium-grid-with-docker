package com.selenium.tests;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to manage WebDriver instances for Chrome browser
 */
public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static WebDriver driver;

    /**
     * Initialize and return Chrome WebDriver instance
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            setupChromeDriver();
        }
        return driver;
    }

    /**
     * Setup Chrome WebDriver with WebDriverManager
     */
    private static void setupChromeDriver() {
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-blink-features=AutomationControlled");
            
            driver = new ChromeDriver(options);
            logger.info("Chrome WebDriver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize Chrome WebDriver", e);
            throw new RuntimeException("Failed to initialize Chrome WebDriver", e);
        }
    }

    private static void setupRemoteChromeDriver() {
    try {
        String remoteUrl = System.getenv("REMOTE_DRIVER_URL");
        if (remoteUrl == null) {
            remoteUrl = "http://chrome:4444/wd/hub"; // Docker network URL
        }
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        driver = new RemoteWebDriver(new URL(remoteUrl), options);
        logger.info("Remote Chrome WebDriver initialized: " + remoteUrl);
    } catch (Exception e) {
        logger.error("Failed to initialize Remote Chrome WebDriver", e);
        throw new RuntimeException("Failed to initialize Remote Chrome WebDriver", e);
    }
}

    /**
     * Close and quit the WebDriver
     */
    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
                driver = null;
                logger.info("Chrome WebDriver closed successfully");
            } catch (Exception e) {
                logger.error("Failed to close Chrome WebDriver", e);
            }
        }
    }
}
