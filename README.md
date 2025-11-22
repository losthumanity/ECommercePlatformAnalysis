# E-Commerce Analytics Platform

Microservices-based analytics platform built with Spring Boot and React for processing e-commerce sales, inventory, and user activity data.

## Architecture

```
React Dashboard (Port 80) → API Gateway (8080) → Analytics Service (8081)
                                    ↓                        ↓
                            Discovery Service (8761)    MySQL + Redis
```

**Services:**
- **Analytics Service** - REST APIs for sales, inventory, and user analytics
- **API Gateway** - Request routing and load balancing
- **Discovery Service** - Eureka service registry
- **Dashboard** - React frontend with real-time charts and metrics

## Tech Stack

**Backend:** Java 17, Spring Boot 3.2, Spring Cloud, MySQL 8.0, Redis 7
**Frontend:** React 18, Vite, Recharts, Axios
**Infrastructure:** Docker, Docker Compose

## Quick Start

```bash
# Start all services
docker-compose up --build

# Access
- Dashboard: http://localhost
- API Gateway: http://localhost:8080
- Eureka: http://localhost:8761
- Adminer: http://localhost:8082
```

Wait ~2 minutes for services to initialize. Dashboard auto-refreshes every 30-60 seconds.

## Development

**Backend (Analytics Service):**
```bash
cd analytics-service
run.bat  # or mvn spring-boot:run
```

**Frontend (Dashboard):**
```bash
cd dashboard
npm install
npm run dev  # http://localhost:3000
```

## API Endpoints

All endpoints via API Gateway (`http://localhost:8080/api/analytics`)

**Sales:**
- `GET /sales/by-category?startDate={date}&endDate={date}` - Sales by category
- `GET /sales/top-products?startDate={date}&endDate={date}&limit={n}` - Top products
- `GET /sales/daily?startDate={date}&endDate={date}` - Daily sales trend
- `GET /sales/total?startDate={date}&endDate={date}` - Total sales

**Inventory:**
- `GET /inventory/status` - All products with stock levels
- `GET /inventory/low-stock?threshold={n}` - Products below threshold

**User Activity:**
- `GET /user-activity/summary?startDate={date}&endDate={date}` - Activity summary
- `GET /user-activity/most-viewed?startDate={date}&endDate={date}&limit={n}` - Top viewed
- `GET /user-activity/unique-users?startDate={date}&endDate={date}` - Unique user count

## Database

**MySQL** (port 3307):
```bash
mysql -h 127.0.0.1 -P 3307 -u ecommerce_user -pecommerce_pass ecommerce_analytics
```

**Adminer UI:** http://localhost:8082 (server: `mysql`, user: `ecommerce_user`, pass: `ecommerce_pass`)

Sample data auto-loaded on startup from `database/init/`.

## Testing

```bash
cd analytics-service
mvn test              # Unit tests
mvn verify            # Integration tests (Testcontainers)
```

## Project Structure

```
├── analytics-service/     # Main business logic + REST APIs
├── api-gateway/           # Spring Cloud Gateway
├── discovery-service/     # Eureka server
├── dashboard/             # React frontend
├── database/init/         # SQL schema + sample data
└── docker-compose.yml     # All services orchestration
```

## Monitoring

Health checks available at `/actuator/health`:
- Analytics: http://localhost:8081/actuator/health
- Gateway: http://localhost:8080/actuator/health
- Discovery: http://localhost:8761/actuator/health

## Notes

- MySQL on port 3307 (not 3306) to avoid conflicts
- Redis cache TTL: 10 minutes
- Dashboard shows backend connection status banner if services unavailable
- All dates in `yyyy-MM-dd` format

---

Built with Spring Boot microservices architecture and React dashboard for real-time analytics visualization.
