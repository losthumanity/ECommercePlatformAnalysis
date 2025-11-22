# E-Commerce Analytics Microservices

A production-ready microservices architecture for e-commerce analytics built with Spring Boot, Spring Cloud, and MySQL.

## 📐 Architecture

```
┌──────────────┐
│   Client     │
└──────┬───────┘
       │
       ▼
┌──────────────────────┐
│   API Gateway        │ (Port 8080)
│   Spring Cloud GW    │
└──────┬───────────────┘
       │
       ├─────────────────────┐
       │                     │
       ▼                     ▼
┌──────────────┐    ┌────────────────┐
│  Discovery   │    │   Analytics    │
│   Service    │◄───┤    Service     │
│  (Eureka)    │    │                │
│ Port 8761    │    │   Port 8081    │
└──────────────┘    └───────┬────────┘
                            │
                    ┌───────┴────────┐
                    │                │
                    ▼                ▼
             ┌──────────┐    ┌──────────┐
             │  MySQL   │    │  Redis   │
             │  :3307   │    │  :6379   │
             └──────────┘    └──────────┘
```

## 🚀 Services

### 1. **Discovery Service** (Eureka Server)
- Service registry and discovery
- Port: 8761
- Dashboard: http://localhost:8761

### 2. **API Gateway** (Spring Cloud Gateway)
- Single entry point for all requests
- Load balancing & routing
- Port: 8080
- Routes all `/api/analytics/**` to Analytics Service

### 3. **Analytics Service**
- Business logic for sales, inventory, and user activity analytics
- Port: 8081
- MySQL database with Redis caching
- Comprehensive REST APIs

## 📊 API Endpoints

### Sales Analytics

```bash
# Get sales by category
GET http://localhost:8080/api/analytics/sales/by-category?startDate=2024-01-01&endDate=2024-12-31

# Get top selling products
GET http://localhost:8080/api/analytics/sales/top-products?startDate=2024-01-01&endDate=2024-12-31&limit=10

# Get daily sales
GET http://localhost:8080/api/analytics/sales/daily?startDate=2024-01-01&endDate=2024-12-31

# Get total sales
GET http://localhost:8080/api/analytics/sales/total?startDate=2024-01-01&endDate=2024-12-31
```

### Inventory Analytics

```bash
# Get inventory status
GET http://localhost:8080/api/analytics/inventory/status

# Get low stock products
GET http://localhost:8080/api/analytics/inventory/low-stock?threshold=50
```

### User Activity Analytics

```bash
# Get activity summary
GET http://localhost:8080/api/analytics/user-activity/summary?startDate=2024-01-01&endDate=2024-12-31

# Get most viewed products
GET http://localhost:8080/api/analytics/user-activity/most-viewed?startDate=2024-01-01&endDate=2024-12-31&limit=10

# Get unique users count
GET http://localhost:8080/api/analytics/user-activity/unique-users?startDate=2024-01-01&endDate=2024-12-31
```

## 🛠️ Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Cloud 2023.0.0**
- **Spring Data JPA**
- **MySQL 8.0**
- **Redis 7**
- **Docker & Docker Compose**
- **JUnit 5 & Mockito**
- **Testcontainers**

## 📦 Prerequisites

- **Docker & Docker Compose** (Required)
- **Java 17** (Optional - only if building outside Docker)
- **Maven 3.8+** (Optional - batch scripts use Docker Maven)

## 🚀 Quick Start (Windows)

### **Method 1: Docker Compose - Everything in Containers (Recommended)**

Start all services with one command:

```bash
docker-compose up --build
```

Wait 2-3 minutes for services to start. Access:
- **Eureka**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Analytics API**: http://localhost:8081
- **Adminer (DB)**: http://localhost:8082

Test the API:
```bash
curl http://localhost:8080/api/analytics/inventory/status
```

Stop everything:
```bash
docker-compose down
```

---

### **Method 2: Using Batch Scripts (No Maven Installation)**

#### Step 1: Start Infrastructure
```bash
docker-compose up -d mysql redis adminer
```

#### Step 2: Run Services (Open 3 separate CMD windows)

**CMD Window 1:**
```bash
cd discovery-service
run.bat
```

**CMD Window 2:**
```bash
cd api-gateway
run.bat
```

**CMD Window 3:**
```bash
cd analytics-service
run.bat
```

---

### **Method 3: Traditional Maven (Requires Maven Installation)**

If you have Maven installed:

```bash
# Build all
build-all.bat

# Or manually for each service
cd discovery-service
mvn clean install
mvn spring-boot:run
```

Install Maven: https://maven.apache.org/download.cgi

## ✅ Verify Setup

After starting services (any method), verify they're running:

```bash
# Check Eureka Dashboard (should show registered services)
# Browser: http://localhost:8761

# Test Analytics API
curl "http://localhost:8080/api/analytics/inventory/status"
```

Or open in browser:
- http://localhost:8080/api/analytics/inventory/status

## 🗄️ Database Access

**Via Adminer (Web UI):**
- URL: http://localhost:8082 ⚠️ (Changed from 8080)
- System: MySQL
- Server: mysql
- Username: ecommerce_user
- Password: ecommerce_pass
- Database: ecommerce_analytics

**Direct MySQL Connection:**
```bash
mysql -h 127.0.0.1 -P 3307 -u ecommerce_user -pecommerce_pass
```

**Note**: MySQL is on port **3307** (not 3306) to avoid conflicts.

## 🧪 Testing

### Run Unit Tests

**Using batch script:**
```bash
cd analytics-service
test.bat
```

**Using Maven (if installed):**
```bash
cd analytics-service
mvn test
```

### Run Integration Tests (with Testcontainers)

```bash
cd analytics-service
mvn verify
```

Integration tests automatically spin up MySQL container via Testcontainers.

## 📈 Sample Usage Scenarios

### Scenario 1: Get Sales Report

```bash
# Get sales by category for last 30 days
curl "http://localhost:8080/api/analytics/sales/by-category?startDate=2024-11-01&endDate=2024-11-30"
```

### Scenario 2: Check Low Stock Items

```bash
# Get products with stock below 50
curl "http://localhost:8080/api/analytics/inventory/low-stock?threshold=50"
```

### Scenario 3: View Top Products

```bash
# Get top 5 selling products
curl "http://localhost:8080/api/analytics/sales/top-products?startDate=2024-01-01&endDate=2024-12-31&limit=5"
```

## 🔧 Configuration

### Port Configuration

| Service | Port | Configuration File |
|---------|------|--------------------|
| Discovery Service | 8761 | `discovery-service/src/main/resources/application.yml` |
| API Gateway | 8080 | `api-gateway/src/main/resources/application.yml` |
| Analytics Service | 8081 | `analytics-service/src/main/resources/application.yml` |

### Database Configuration

Edit `analytics-service/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/ecommerce_analytics
    username: ecommerce_user
    password: ecommerce_pass
```

### Redis Configuration

```yaml
spring:
  redis:
    host: localhost
    port: 6379
```

## 📊 Monitoring & Health Checks

Each service exposes actuator endpoints:

```bash
# Discovery Service Health
curl http://localhost:8761/actuator/health

# API Gateway Health
curl http://localhost:8080/actuator/health

# Analytics Service Health
curl http://localhost:8081/actuator/health
```

## 🐛 Troubleshooting

### Service Not Registering with Eureka

1. Ensure Discovery Service is running first
2. Check logs for connection errors
3. Verify `eureka.client.service-url.defaultZone` in application.yml

### Database Connection Issues

```bash
# Check if MySQL is running
docker ps | grep mysql

# View MySQL logs
docker logs ecommerce-mysql

# Test connection
mysql -h 127.0.0.1 -P 3307 -u ecommerce_user -pecommerce_pass -e "SHOW DATABASES;"
```

### Redis Connection Issues

```bash
# Check if Redis is running
docker ps | grep redis

# Test Redis
docker exec -it ecommerce-redis redis-cli ping
# Should return: PONG
```

## 🏗️ Project Structure

```
Ecommerce/
├── docker-compose.yml
├── database/
│   └── init/
│       ├── 01-schema.sql
│       └── 02-sample-data.sql
├── discovery-service/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/ecommerce/discovery/
│       └── resources/application.yml
├── api-gateway/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/ecommerce/gateway/
│       └── resources/application.yml
└── analytics-service/
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/com/ecommerce/analytics/
        │   │   ├── controller/
        │   │   ├── service/
        │   │   ├── repository/
        │   │   ├── model/
        │   │   └── dto/
        │   └── resources/application.yml
        └── test/
            └── java/com/ecommerce/analytics/
```

## 🔐 Security Considerations

This is a development setup. For production:

1. Enable Spring Security
2. Add OAuth2/JWT authentication
3. Use secrets management (Vault, AWS Secrets Manager)
4. Enable HTTPS/TLS
5. Add rate limiting
6. Configure proper CORS policies

## 📝 Next Steps

- [ ] Add Spring Cloud Config for centralized configuration
- [ ] Implement circuit breakers (Resilience4j)
- [ ] Add distributed tracing (Zipkin/Sleuth)
- [ ] Set up CI/CD pipeline
- [ ] Add Kafka for event streaming
- [ ] Implement authentication/authorization
- [ ] Add API documentation (Swagger/OpenAPI)

## 📄 License

This project is for educational purposes.

## 👥 Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

---

**Built with ❤️ using Spring Boot & Spring Cloud**
