FROM maven:3.9.6-eclipse-temurin-11

WORKDIR /app

ENV RUN_MODE=remote

COPY pom.xml .

RUN mvn dependency:go-offline

COPY . .

# Wait for hub to be ready before running tests
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# CMD ["sh", "-c", "curl -s http://selenium-hub:4444/wd/hub/status || true && mvn clean test"]
CMD ["sh", "-c", "until curl -s http://selenium-hub:4444/status > /dev/null; do echo 'Waiting for Selenium Hub...'; sleep 2; done; echo 'Hub is ready'; mvn test -Dtestng.suite=testng-parallel.xml"]