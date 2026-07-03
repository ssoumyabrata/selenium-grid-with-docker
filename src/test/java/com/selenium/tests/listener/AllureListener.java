package com.selenium.tests.listener;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import com.selenium.tests.util.ScreenshotUtil;
import com.selenium.tests.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllureListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(AllureListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                String testName = result.getName();
                ScreenshotUtil.takeStepScreenshot(driver, testName, "failure");
                logger.info("Screenshot taken on failure for test: {}", testName);
            } else {
                logger.warn("WebDriver is null in AllureListener.onTestFailure");
            }
        } catch (Exception e) {
            logger.error("Exception in AllureListener.onTestFailure", e);
        }
    }
}