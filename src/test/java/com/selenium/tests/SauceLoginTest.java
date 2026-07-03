package com.selenium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({com.selenium.tests.listener.AllureListener.class})
public class SauceLoginTest extends BaseTest {

    @Test
    public void validLogin_shouldSucceed() {
        WebDriver driver = getDriver();
        driver.get("https://www.saucedemo.com/");
        step("01_navigate_home");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        step("02_enter_username");

        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        step("03_enter_password");

        driver.findElement(By.id("login-button")).click();
        step("04_after_login");

        boolean loggedIn = driver.findElements(By.cssSelector(".inventory_list")).size() > 0;
        assert loggedIn : "Login should succeed and inventory should be visible";
    }

    @Test
    public void invalidLogin_shouldShowError() {
        WebDriver driver = getDriver();
        driver.get("https://www.saucedemo.com/");
        step("01_navigate_home_invalid");

        driver.findElement(By.id("user-name")).sendKeys("wrong_user");
        step("02_enter_bad_username");

        driver.findElement(By.id("password")).sendKeys("wrong_password");
        step("03_enter_bad_password");

        driver.findElement(By.id("login-button")).click();
        step("04_after_login_invalid");

        boolean errorShown = driver.findElements(By.cssSelector("[data-test='error']")).size() > 0;
        assert errorShown : "Error should be shown for invalid login";
    }
}
