package com.selenium.tests;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;

import com.selenium.tests.util.ScreenshotUtil;

public class BaseTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());


    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, ITestContext context) {
        String browser = null;
        if (context != null && context.getCurrentXmlTest() != null) {
            browser = context.getCurrentXmlTest().getParameter("browser");
        }
        if (browser == null || browser.isEmpty()) {
            browser = System.getProperty("browser", System.getenv().getOrDefault("BROWSER", "chrome"));
        }
        logger.info("Initializing driver for browser: {}", browser);
        DriverManager.initDriver(browser);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                DriverManager.quitDriver();
            } catch (Exception e) {
                logger.error("Error quitting driver", e);
            }
        }
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected void step(String name) {
        WebDriver d = getDriver();
        if (d != null) {
            ScreenshotUtil.takeScreenshot(d, name);
        }
        logger.info("STEP: {}", name);
    }
}
