@echo off
REM Run API Gateway using Docker Maven

echo Starting API Gateway on port 8080...
echo Press Ctrl+C to stop
echo.

docker run --rm -it ^
  -v "%cd%\api-gateway":/app ^
  -w /app ^
  -p 8080:8080 ^
  --network ecommerce-net ^
  maven:3.9-eclipse-temurin-17 ^
  mvn spring-boot:run
