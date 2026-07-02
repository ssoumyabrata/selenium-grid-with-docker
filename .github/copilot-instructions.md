- [x] Verify that the copilot-instructions.md file in the .github directory is created.

- [x] Clarify Project Requirements
  - Project type: Maven Java project
  - Language: Java 11
  - Frameworks: Selenium WebDriver 4.15.0, TestNG, WebDriverManager
  - Browser: Chrome
  - Purpose: Basic Selenium automation tests for Chrome browser

- [x] Scaffold the Project
  - Created Maven project structure
  - Created src/main/java and src/test/java directories
  - Created pom.xml with all required dependencies
  - Scaffolding completed successfully

- [x] Customize the Project
  - Created DriverManager.java utility class for WebDriver management
  - Created ChromeBasicTest.java with TestNG test cases
  - Created testng.xml configuration for test execution
  - Configured logback.xml for logging
  - Added WebDriverManager for automatic ChromeDriver management

- [x] Install Required Extensions
  - No extensions required; Maven and Java support built into VS Code

- [ ] Compile the Project
  - Run: `mvn clean install`
  - Resolve any compilation errors

- [ ] Create and Run Task
  - Create VS Code task for running Maven tests

- [ ] Launch the Project
  - Run: `mvn clean test`

- [ ] Ensure Documentation is Complete
  - README.md created with setup instructions
  - pom.xml configured with all dependencies and build plugins
