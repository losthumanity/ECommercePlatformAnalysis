@echo off
echo ========================================
echo E-Commerce Analytics Microservices
echo ========================================
echo.
echo Starting all services using Docker Compose...
echo This will build and start:
echo   - MySQL (port 3307)
echo   - Redis (port 6379)
echo   - Adminer (port 8082)
echo   - Discovery Service (port 8761)
echo   - API Gateway (port 8080)
echo   - Analytics Service (port 8081)
echo.
echo First time build may take 5-10 minutes...
echo.
echo Press Ctrl+C to stop all services
echo ========================================
echo.

docker-compose up --build
