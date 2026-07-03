package com.selenium.tests.util;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Allure;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreenshotUtil {
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtil.class);

    public static void takeScreenshot(WebDriver driver, String testName) {
        takeStepScreenshot(driver, testName, "screenshot");
    }

    public static void takeStepScreenshot(WebDriver driver, String testName, String stepName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);
            String filename = testName + "_" + stepName + "_" + System.currentTimeMillis() + ".png";
            Path screenshotPath = Paths.get("target/screenshots", filename);
            Files.createDirectories(screenshotPath.getParent());
            Files.copy(screenshot.toPath(), screenshotPath);
            try (InputStream is = Files.newInputStream(screenshotPath)) {
                Allure.addAttachment(stepName + " - " + filename, is);
            }
            logger.info("Screenshot saved: " + screenshotPath);
        } catch (Exception e) {
            logger.error("Failed to take screenshot", e);
        }
    }
}