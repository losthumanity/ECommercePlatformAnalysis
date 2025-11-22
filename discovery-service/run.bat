@echo off
REM Run Discovery Service using Docker Maven

echo Starting Discovery Service on port 8761...
echo Press Ctrl+C to stop
echo.

docker run --rm -it ^
  -v "%cd%\discovery-service":/app ^
  -w /app ^
  -p 8761:8761 ^
  --network ecommerce-net ^
  maven:3.9-eclipse-temurin-17 ^
  mvn spring-boot:run
