package com.selenium.tests.listener;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import com.selenium.tests.util.ScreenshotUtil;

public class AllureListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver driver = null;
        try {
            driver = (WebDriver) testClass.getClass().getDeclaredField("driver").get(testClass);
            ScreenshotUtil.takeScreenshot(driver, result.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}