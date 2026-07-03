package com.selenium.tests;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to manage WebDriver instances for Chrome browser
 */
public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void initDriver(String browser) {
        if (DRIVER.get() != null) {
            return;
        }

        if (isRemoteMode()) {
            setupRemoteDriver(browser);
        } else {
            setupLocalDriver(browser);
        }
    }

    private static void setupLocalDriver(String browser) {
        try {
            if (browser == null) {
                browser = System.getProperty("browser", System.getenv().getOrDefault("BROWSER", "chrome"));
            }

            switch (browser.toLowerCase()) {
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--disable-blink-features=AutomationControlled");
                    edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
                    edgeOptions.setExperimentalOption("useAutomationExtension", false);
                    edgeOptions.addArguments("--disable-dev-shm-usage");
                    edgeOptions.addArguments("--no-sandbox");
                    edgeOptions.addArguments("--window-size=1920,1080");
                    DRIVER.set(new EdgeDriver(edgeOptions));
                    logger.info("Local Edge WebDriver initialized");
                    break;
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
                    chromeOptions.setExperimentalOption("useAutomationExtension", false);
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    DRIVER.set(new ChromeDriver(chromeOptions));
                    logger.info("Local Chrome WebDriver initialized");

                    break;
            }
        } catch (Exception e) {
            logger.error("Failed to initialize local WebDriver", e);
            throw new RuntimeException(e);
        }
    }

    private static void setupRemoteDriver(String browser) {
        try {
            String remoteUrl = System.getProperty("remote.url");
            if (remoteUrl == null || remoteUrl.isEmpty()) {
                remoteUrl = System.getenv().getOrDefault("REMOTE_DRIVER_URL", "http://selenium-hub:4444/wd/hub");
            }

            if (browser == null) {
                browser = System.getProperty("browser", System.getenv().getOrDefault("BROWSER", "chrome"));
            }

            if (browser.equalsIgnoreCase("edge")) {
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--disable-blink-features=AutomationControlled");
                edgeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
                edgeOptions.setExperimentalOption("useAutomationExtension", false);
                edgeOptions.addArguments("--disable-dev-shm-usage");
                edgeOptions.addArguments("--no-sandbox");
                edgeOptions.addArguments("--window-size=1920,1080");
                DRIVER.set(new RemoteWebDriver(new URL(remoteUrl), edgeOptions));
                logger.info("Remote Edge WebDriver initialized: " + remoteUrl);
            } else {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                chromeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
                chromeOptions.setExperimentalOption("useAutomationExtension", false);
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--window-size=1920,1080");
                DRIVER.set(new RemoteWebDriver(new URL(remoteUrl), chromeOptions));
                logger.info("Remote Chrome WebDriver initialized: " + remoteUrl);
            }
        } catch (Exception e) {
            logger.error("Failed to initialize Remote WebDriver", e);
            throw new RuntimeException(e);
        }
    }

    public static void quitDriver() {
        WebDriver d = DRIVER.get();
        if (d != null) {
            try {
                d.quit();
                logger.info("WebDriver closed successfully");
            } catch (Exception e) {
                logger.error("Failed to close WebDriver", e);
            } finally {
                DRIVER.remove();
            }
        }
    }

    private static boolean isRemoteMode() {
        String remoteProp = System.getProperty("remote");
        if (remoteProp != null && (remoteProp.equalsIgnoreCase("true") || remoteProp.equalsIgnoreCase("yes"))) {
            return true;
        }
        String runMode = System.getenv("RUN_MODE");
        if (runMode != null && runMode.equalsIgnoreCase("remote")) {
            return true;
        }
        String remoteEnv = System.getenv("REMOTE");
        if (remoteEnv != null && (remoteEnv.equalsIgnoreCase("true") || remoteEnv.equalsIgnoreCase("yes"))) {
            return true;
        }
        return false;
    }
}
