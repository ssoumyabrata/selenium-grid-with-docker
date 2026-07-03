package com.selenium.tests.util;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Allure;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreenshotUtil {
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtil.class);

    public static void takeScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);
            String filename = testName + "_" + System.currentTimeMillis() + ".png";
            Path screenshotPath = Paths.get("target/screenshots", filename);
            Files.createDirectories(screenshotPath.getParent());
            Files.copy(screenshot.toPath(), screenshotPath);
            Allure.addAttachment(filename, Files.newInputStream(screenshotPath));
            logger.info("Screenshot saved: " + screenshotPath);
        } catch (Exception e) {
            logger.error("Failed to take screenshot", e);
        }
    }
}