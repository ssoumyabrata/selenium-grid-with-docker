# Selenium Basic Tests

This is a basic Selenium WebDriver test project configured to run automated browser tests in Chrome using Java and Maven.

## Project Overview

- **Language**: Java 11
- **Build Tool**: Maven
- **Test Framework**: TestNG
- **Browser**: Chrome
- **Selenium Version**: 4.15.0

## Prerequisites

- Java 11 or higher (currently have Java 17 ✓)
- Maven 3.8.0 or higher
- Chrome browser installed
- VS Code with Java and Maven extensions (optional)

## Maven Installation

If Maven is not installed on your system:

### Option 1: Using the Installation Script
```bash
cd d:\Learning - code\selenium-basic
install-maven.bat
```

### Option 2: Manual Installation
1. Download Maven from [Apache Maven](https://maven.apache.org/download.cgi)
2. Extract to a location (e.g., `C:\apache-maven-3.8.6`)
3. Add the `bin` folder to your PATH environment variable
4. Verify: `mvn -version`

## Project Structure

```
selenium-basic/
├── src/
│   ├── main/
│   │   └── java/com/selenium/tests/
│   │       └── WebDriverManager.java
│   └── test/
│       └── java/com/selenium/tests/
│           └── ChromeBasicTest.java
├── pom.xml
├── testng.xml
└── README.md
```

## Setup Instructions

1. **Clone/Open the project in VS Code**
   ```bash
   cd d:\Learning - code\selenium-basic
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Run tests**
   ```bash
   mvn test
   ```

## Running Tests

Run all tests:
```bash
mvn clean test
```

Run a specific test class:
```bash
mvn test -Dtest=ChromeBasicTest
```

## Test Files

- **ChromeBasicTest.java**: Basic Selenium test that opens Chrome and navigates to a website

## Configuration

The project uses WebDriverManager to automatically download and manage ChromeDriver, so no manual driver setup is needed.

## Logging

Logging is configured using Logback. Log files and console output can be configured in `logback.xml`.

## Notes

- Tests will open actual Chrome browser windows during execution
- WebDriverManager automatically handles ChromeDriver version management
- TestNG configuration is defined in `testng.xml`
