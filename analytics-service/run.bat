@echo off
REM Run Analytics Service using Docker Maven

echo Starting Analytics Service on port 8081...
echo Press Ctrl+C to stop
echo.

docker run --rm -it ^
  -v "%cd%\analytics-service":/app ^
  -w /app ^
  -p 8081:8081 ^
  --network ecommerce-net ^
  maven:3.9-eclipse-temurin-17 ^
  mvn spring-boot:run
