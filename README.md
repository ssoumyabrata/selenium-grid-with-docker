# Selenium Basic Tests - Advanced Setup

Automated browser testing framework with Selenium Grid, Docker Compose, parallel test execution via TestNG, and CI/CD integration using GitHub Actions.

## Project Architecture

This project demonstrates an **enterprise-grade test automation setup**:

- **Local Mode**: Run tests directly on your machine with ChromeDriver/EdgeDriver
- **Remote Mode (Docker)**: Run tests against Selenium Grid with isolated browser nodes
- **Parallel Execution**: TestNG configured for concurrent test runs (Chrome: 2 parallel, Edge: 1)
- **CI/CD**: GitHub Actions pipeline with automated Allure report generation
- **Reporting**: Allure reports with test artifacts (screenshots, logs)

## Tech Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| Java | 11+ | Programming language |
| Maven | 3.9.6 | Build & dependency management |
| Selenium WebDriver | 4.44.0 | Browser automation |
| TestNG | 7.7.1 | Test framework with parallel execution |
| Allure | 2.21.0 | Test reporting & analytics |
| Docker Compose | Latest | Container orchestration for Selenium Grid |
| Selenium Hub | 4.37.0 | Grid coordinator |
| Selenium Node Chrome | 4.37.0 | Browser node (2 replicas) |
| Selenium Node Edge | 4.37.0 | Browser node (1 replica) |

## Project Structure

```
selenium-basic/
├── src/
│   ├── main/java/com/selenium/tests/
│   │   └── DriverManager.java              # WebDriver factory (local/remote)
│   └── test/java/com/selenium/tests/
│       ├── BaseTest.java                   # Base test class with setup/teardown
│       ├── SauceLoginTest.java             # Login test cases
│       ├── SauceCartTest.java              # Cart/e-commerce tests
│       ├── listener/
│       │   └── AllureListener.java         # Allure integration
│       └── util/                           # Test utilities
├── .github/workflows/
│   └── selenium-tests.yml                  # GitHub Actions CI/CD pipeline
├── pom.xml                                 # Maven configuration
├── Dockerfile                              # Docker image for test container
├── dc-hub.yml                              # Docker Compose: Selenium Grid setup
├── testng.xml                              # TestNG suite config (sequential)
├── testng-parallel.xml                     # TestNG suite config (parallel)
├── target/
│   ├── allure-results/                     # Allure test results JSON
│   ├── allure-report/                      # Generated Allure HTML report
│   ├── screenshots/                        # Test failure screenshots
│   └── surefire-reports/                   # TestNG reports
└── logs/                                   # Test execution logs
```

## Prerequisites

### For Local Execution
- Java 11 or higher
- Maven 3.8.0+
- Chrome or Edge browser installed

### For Docker/Selenium Grid Execution
- Docker & Docker Compose
- 8GB+ RAM recommended
- Ports 4442, 4443, 4444 available for Selenium Hub

### For CI/CD
- GitHub repository
- GitHub Actions enabled (default)

## Setup Instructions

### 1. Clone/Open Project
```bash
cd d:\Learning - code\selenium-basic
```

### 2. Install Dependencies
```bash
mvn clean install
```

## Running Tests

### Option A: Local Execution (Desktop Browser)

**Run all tests sequentially:**
```bash
mvn clean test -Dtest=SauceLoginTest,SauceCartTest
```

**Run with local mode explicitly:**
```bash
mvn clean test -Dremote=false
```

**Run specific test class:**
```bash
mvn test -Dtest=SauceLoginTest
```

**Set browser type:**
```bash
mvn test -Dbrowser=edge
```

### Option B: Docker - Selenium Grid (Recommended for CI/Local Testing)

**Start Selenium Grid with test execution:**
```bash
docker compose -f dc-hub.yml up
```

**Run in background (detached):**
```bash
docker compose -f dc-hub.yml up -d
```

**Stop all services:**
```bash
docker compose -f dc-hub.yml down -v
```

**View Docker logs:**
```bash
docker compose -f dc-hub.yml logs -f
```

**Scale browser nodes:**
```bash
docker compose -f dc-hub.yml up --scale chrome=4 --scale edge=2
```

**Access Selenium Grid UI:**
- Hub: http://localhost:4444/ui
- Grid Console: http://localhost:4444

### Option C: GitHub Actions (Automated CI/CD)

**Trigger workflow automatically on:**
- Push to `main` or `develop` branches
- Pull requests
- Manual trigger via "Workflow dispatch"

**View results:**
1. Go to **Actions** tab in GitHub
2. Select workflow run
3. Download artifacts:
   - `allure-report/` - HTML test report
   - `screenshots/` - Failure screenshots
   - `test-logs/` - Execution logs

## Configuration

### DriverManager.java - Local vs Remote Mode

The `DriverManager` class automatically switches between modes based on environment:

**Local Mode** (default):
```java
// ChromeDriver or EdgeDriver runs locally
mvn test -Dremote=false
```

**Remote Mode** (Selenium Grid):
```java
// Connects to Selenium Hub at http://selenium-hub:4444/wd/hub
mvn test -Dremote=true
// OR in Docker:
docker compose -f dc-hub.yml up
```

**Environment Variables:**
```
RUN_MODE=remote         # Enables remote mode
REMOTE_DRIVER_URL       # Custom Grid URL (default: http://selenium-hub:4444/wd/hub)
BROWSER=chrome|edge     # Browser selection
```

### TestNG Parallel Execution

**testng-parallel.xml** configuration:

| Test Suite | Parallel Mode | Threads | Browsers |
|-----------|---|---|---|
| Chrome Tests | classes | 2 | Runs SauceLoginTest + SauceCartTest simultaneously |
| Edge Tests | classes | 1 | Runs SauceLoginTest, then SauceCartTest sequentially |
| Both Suites | tests | - | Chrome and Edge execute in parallel with each other |

**Run with parallel config:**
```bash
mvn test -Dtestng.suite=testng-parallel.xml
```

**Modify thread count in testng-parallel.xml:**
```xml
<test name="Chrome Tests" parallel="classes" thread-count="4">
```

### Allure Reporting

**View report locally after Docker execution:**
```bash
allure serve target/allure-results
```

**Generate HTML report:**
```bash
allure generate target/allure-results -o target/allure-report --clean
```

**Access in CI/CD:**
- Reports automatically uploaded to GitHub Artifacts
- PR comments include report links
- Published to GitHub Pages on `main` branch

## Docker Compose Architecture (dc-hub.yml)

```yaml
Services:
├── selenium-hub (1 instance)
│   ├── Port: 4444 (Grid API)
│   ├── Healthcheck: Monitors service availability
│   └── Role: Coordinates browser nodes
│
├── chrome nodes (2 instances)
│   ├── Image: selenium/node-chrome:4.37.0
│   ├── Max sessions: 1 per node
│   └── Shared memory: 2GB (for stability)
│
├── edge nodes (1 instance)
│   ├── Image: selenium/node-edge:4.37.0
│   ├── Max sessions: 1 per node
│   └── Shared memory: 2GB
│
└── automation container (test runner)
    ├── Builds from Dockerfile
    ├── Environment: RUN_MODE=remote
    ├── Waits for hub healthcheck
    └── Executes: mvn test -Dtestng.suite=testng-parallel.xml
```

**Volumes (persisted to host):**
- `target/allure-results/` - Test artifacts
- `target/allure-report/` - Generated reports
- `target/screenshots/` - Test screenshots
- `logs/` - Application logs

## GitHub Actions CI/CD Pipeline (`.github/workflows/selenium-tests.yml`)

**Workflow Steps:**

1. **Checkout** - Clone repository
2. **Build** - `docker compose build`
3. **Run Tests** - `docker compose up --abort-on-container-exit`
4. **Collect Reports** - Allure results from volume mounts
5. **Upload Artifacts** - Save to GitHub for 30 days
6. **Comment PR** - Add report link to pull requests
7. **Deploy Report** - Publish to GitHub Pages (main branch only)
8. **Cleanup** - `docker compose down -v`

**Triggered on:**
- Push to `main` or `develop`
- Pull requests
- Manual workflow dispatch

**Access Results:**
- **Artifacts**: Actions tab → Workflow run → Artifacts section
- **PR Comments**: Automatic link in pull request discussion
- **GitHub Pages**: `https://your-username.github.io/your-repo/{run_number}/`

## Common Commands

```bash
# Clean build
mvn clean install

# Local execution - sequential
mvn clean test

# Local execution - parallel
mvn test -Dtestng.suite=testng-parallel.xml

# Remote execution (Docker)
docker compose -f dc-hub.yml up

# Run with custom browser
mvn test -Dbrowser=edge

# Run specific test
mvn test -Dtest=SauceLoginTest

# Generate Allure report
allure serve target/allure-results

# View Docker logs
docker compose -f dc-hub.yml logs -f selenium-tests

# Check Grid status
curl http://localhost:4444/status

# Cleanup Docker
docker compose -f dc-hub.yml down -v
```

## Test Classes

### SauceLoginTest.java
- User login scenarios
- Credentials validation
- Error handling

### SauceCartTest.java
- Shopping cart operations
- Product addition/removal
- Checkout flow

### BaseTest.java
- Common setup/teardown logic
- Browser initialization
- Report listeners

## Best Practices Implemented

✅ **Thread-safe WebDriver management** - ThreadLocal pattern  
✅ **Flexible execution modes** - Local or Grid-based  
✅ **Parallel test execution** - Different thread counts per browser  
✅ **Screenshot on failure** - Automatic failure evidence  
✅ **Comprehensive logging** - Logback with configurable levels  
✅ **CI/CD integration** - Automated GitHub Actions pipeline  
✅ **Artifact retention** - 30-day retention policy  
✅ **Scalable architecture** - Easy to add/remove nodes  
✅ **Report analytics** - Allure for detailed test insights  

## Troubleshooting

### Docker Connection Issues
```bash
# Verify hub is running
docker compose -f dc-hub.yml ps

# Check hub health
curl http://localhost:4444/status

# Wait longer in tests
# Edit Dockerfile CMD with increased timeout
```

### Port Already in Use
```bash
# Kill processes using ports 4442-4444
netstat -ano | findstr :4444
taskkill /PID <PID> /F
```

### Out of Memory
```bash
# Increase Docker resources or reduce replicas
docker compose -f dc-hub.yml up --scale chrome=1 --scale edge=1
```

### Tests Failing in Docker
- Ensure `RUN_MODE=remote` is set in Dockerfile
- Check hub is healthy: `docker compose logs selenium-hub`
- Verify network connectivity: `docker network ls`

## Notes

- Tests open actual browser windows during execution
- ThreadLocal WebDriver prevents cross-test contamination
- Allure listeners capture screenshots and logs automatically
- Docker Compose requires elevated permissions on some systems
- Network isolation in Docker ensures test stability

## Next Steps

- Add more test cases to `SauceLoginTest` and `SauceCartTest`
- Integrate with cloud providers (BrowserStack, Sauce Labs)
- Set up Allure TestOps for metrics dashboard
- Implement retry logic for flaky tests
- Add performance/lighthouse testing
