package com.selenium.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({com.selenium.tests.listener.AllureListener.class})
public class SauceCartTest extends BaseTest {

    @Test
    public void addItemToCart_shouldAppearInCart() {
        WebDriver driver = getDriver();
        driver.get("https://www.saucedemo.com/");
        step("01_navigate_home");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        step("02_logged_in");

        List<WebElement> addButtons = driver.findElements(By.cssSelector(".inventory_list .inventory_item button"));
        if (!addButtons.isEmpty()) {
            addButtons.get(0).click();
            step("03_added_first_item");
        }

        driver.findElement(By.cssSelector(".shopping_cart_link")).click();
        step("04_open_cart");

        boolean inCart = driver.findElements(By.cssSelector(".cart_item")).size() > 0;
        assert inCart : "Added item should be present in cart";
    }

    @Test
    public void checkout_flow_shouldReachOverview() {
        WebDriver driver = getDriver();
        driver.get("https://www.saucedemo.com/");
        step("01_navigate_home");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        step("02_logged_in");

        List<WebElement> addButtons = driver.findElements(By.cssSelector(".inventory_list .inventory_item button"));
        if (!addButtons.isEmpty()) {
            addButtons.get(0).click();
            step("03_added_first_item");
        }

        driver.findElement(By.cssSelector(".shopping_cart_link")).click();
        step("04_open_cart");

        driver.findElement(By.id("checkout")).click();
        step("05_checkout_info");

        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        step("06_entered_checkout_info");

        driver.findElement(By.id("continue")).click();
        step("07_overview");

        boolean overviewExists = driver.findElements(By.id("checkout_summary_container")).size() > 0;
        assert overviewExists : "Checkout overview should be shown";
    }
}
