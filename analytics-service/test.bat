@echo off
REM Test Analytics Service using Docker Maven

echo Running tests for Analytics Service...
echo.

docker run --rm -it ^
  -v "%cd%\analytics-service":/app ^
  -w /app ^
  maven:3.9-eclipse-temurin-17 ^
  mvn test

echo.
echo ========================================
echo Tests Complete!
echo ========================================
