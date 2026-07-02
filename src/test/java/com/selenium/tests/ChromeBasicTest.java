// package com.selenium.tests;

// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;
// import org.testng.annotations.AfterClass;
// import org.testng.annotations.BeforeClass;
// import org.testng.annotations.Test;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// /**
//  * Basic Selenium test class for Chrome browser
//  */
// public class ChromeBasicTest {
//     private static final Logger logger = LoggerFactory.getLogger(ChromeBasicTest.class);
//     private WebDriver driver;

//     @BeforeClass
//     public void setUp() {
//         logger.info("Setting up WebDriver");
//         driver = DriverManager.getDriver();
//     }

//     @Test
//     public void testGoogleSearch() {
//         try {
//             logger.info("Starting Google search test");
            
//             // Navigate to Google
//             driver.navigate().to("https://www.google.com");
//             logger.info("Navigated to Google.com");
            
//             // Verify page title
//             String title = driver.getTitle();
//             logger.info("Page title: " + title);
//             assert title.contains("Google") : "Page title should contain 'Google'";
            
//             // Find search box and search for Selenium
//             var searchBox = driver.findElement(By.name("q"));
//             searchBox.sendKeys("Selenium WebDriver");
//             searchBox.submit();
//             logger.info("Performed search for 'Selenium WebDriver'");
            
//             // Wait and verify results
//             Thread.sleep(2000);
//             String resultPageTitle = driver.getTitle();
//             logger.info("Result page title: " + resultPageTitle);
//             assert resultPageTitle.contains("Selenium WebDriver") : "Results should contain 'Selenium WebDriver'";
            
//             logger.info("Google search test completed successfully");
//         } catch (Exception e) {
//             logger.error("Test failed: ", e);
//             throw new RuntimeException("Test execution failed", e);
//         }
//     }

//     @Test
//     public void testPageNavigation() {
//         try {
//             logger.info("Starting page navigation test");
            
//             // Navigate to a website
//             driver.navigate().to("https://www.example.com");
//             logger.info("Navigated to example.com");
            
//             // Verify page title
//             String title = driver.getTitle();
//             logger.info("Page title: " + title);
//             assert title.contains("Example") : "Page title should contain 'Example'";
            
//             logger.info("Page navigation test completed successfully");
//         } catch (Exception e) {
//             logger.error("Test failed: ", e);
//             throw new RuntimeException("Test execution failed", e);
//         }
//     }

//     @AfterClass
//     public void tearDown() {
//         logger.info("Tearing down WebDriver");
//         DriverManager.quitDriver();
//     }
// }
