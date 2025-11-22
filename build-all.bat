@echo off
REM Build script for Windows using Docker Maven container

echo Building Discovery Service...
docker run --rm -v "%cd%\discovery-service":/app -w /app maven:3.9-eclipse-temurin-17 mvn clean package -DskipTests

echo.
echo Building API Gateway...
docker run --rm -v "%cd%\api-gateway":/app -w /app maven:3.9-eclipse-temurin-17 mvn clean package -DskipTests

echo.
echo Building Analytics Service...
docker run --rm -v "%cd%\analytics-service":/app -w /app maven:3.9-eclipse-temurin-17 mvn clean package -DskipTests

echo.
echo ========================================
echo Build Complete!
echo ========================================
echo JAR files created in each service's target/ directory
