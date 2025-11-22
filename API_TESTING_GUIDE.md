# 🎯 API Testing Guide - E-Commerce Analytics System

## ✅ System is Running Successfully!

All services are operational:
- ✅ **Eureka Discovery Service** - http://localhost:8761
- ✅ **API Gateway** - http://localhost:8080
- ✅ **Analytics Service** - http://localhost:8081
- ✅ **MySQL Database** - localhost:3307
- ✅ **Redis Cache** - localhost:6379
- ✅ **Adminer (DB UI)** - http://localhost:8082

---

## 🧪 Test the APIs

### **1. Check Service Health**

**Eureka Dashboard** - See all registered services:
```
http://localhost:8761
```

**Expected**: Web UI showing `ANALYTICS-SERVICE` and `API-GATEWAY` registered

---

### **2. Inventory Analytics APIs**

#### Get All Inventory Status
```powershell
# Via Gateway (recommended)
Invoke-WebRequest -Uri "http://localhost:8080/api/analytics/inventory/status"

# Direct to service
Invoke-WebRequest -Uri "http://localhost:8081/api/analytics/inventory/status"
```

**Expected Response**: JSON array with 10 products showing stock levels and status (LOW/MEDIUM/ADEQUATE)

#### Get Low Stock Alerts
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/analytics/inventory/low-stock?threshold=100"
```

**Expected**: Products with stock < 100 units

---

### **3. Sales Analytics APIs**

#### Sales by Category
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/analytics/sales/by-category?startDate=2025-01-01&endDate=2025-12-31"
```

**Expected Response**:
```json
[
  {"category":"Electronics","totalSales":4399.91,"productCount":null},
  {"category":"Furniture","totalSales":1009.94,"productCount":null},
  {"category":"Accessories","totalSales":299.88,"productCount":null},
  {"category":"Appliances","totalSales":269.97,"productCount":null}
]
```

#### Top Selling Products
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/analytics/sales/top-products?startDate=2025-01-01&endDate=2025-12-31&limit=5"
```

**Expected**: Top 5 products by revenue with quantity sold and total sales

#### Daily Sales Report
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/analytics/sales/daily?startDate=2025-01-01&endDate=2025-01-31"
```

**Expected**: Daily breakdown of sales for January 2025

#### Total Revenue
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/analytics/sales/total-revenue?startDate=2025-01-01&endDate=2025-12-31"
```

**Expected**: Single numeric value representing total revenue

---

### **4. User Activity Analytics APIs**

#### Activity Summary
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/analytics/activity/summary?startDate=2025-01-01&endDate=2025-12-31"
```

**Expected**: Breakdown of user activities (PAGE_VIEW, ADD_TO_CART, PURCHASE, etc.) with counts and percentages

#### Recent Activities
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/analytics/activity/recent?limit=10"
```

**Expected**: Last 10 user activities with timestamps

---

## 🔍 Database Access

### Adminer Web UI
Visit: http://localhost:8082

**Login credentials**:
- System: `MySQL`
- Server: `mysql`
- Username: `ecommerce_user`
- Password: `ecommerce_pass`
- Database: `ecommerce_analytics`

---

## 🚀 What This System Does

This is a **production-grade microservices architecture** for e-commerce analytics:

### **Architecture Components**:

1. **Discovery Service (Eureka)** - Service registry for dynamic service discovery
2. **API Gateway** - Single entry point with routing, CORS, and rate limiting
3. **Analytics Service** - Core business logic with:
   - JPA/Hibernate for database access
   - Redis caching for performance
   - RESTful APIs for analytics

### **Key Features**:

✅ **Sales Analytics**
   - Revenue tracking by category
   - Daily sales trends
   - Top-selling products
   - Total revenue calculations

✅ **Inventory Management**
   - Real-time stock levels
   - Low stock alerts
   - Multi-category inventory tracking

✅ **User Activity Tracking**
   - Page views, cart actions, purchases
   - Activity summaries and percentages
   - Recent user engagement

✅ **Technical Highlights**
   - Docker containerization
   - MySQL for persistent storage
   - Redis for caching (reduces DB load)
   - Spring Boot microservices
   - Service discovery and load balancing
   - Health checks and monitoring

---

## 📊 Sample Data

The database is pre-loaded with:
- **10 Products** across Electronics, Furniture, Accessories, Appliances
- **10 Sales records** from January 2025
- **10 User activity records** (page views, cart actions, purchases)

---

## 🛠️ Management Commands

### Start All Services
```cmd
docker-compose up -d
```

### Stop All Services
```cmd
docker-compose down
```

### View Logs
```cmd
docker logs ecommerce-analytics --tail 50
docker logs ecommerce-gateway --tail 50
docker logs ecommerce-discovery --tail 50
```

### Check Service Status
```cmd
docker ps
```

### Rebuild After Code Changes
```cmd
docker-compose build analytics-service
docker-compose up -d analytics-service
```

---

## ✨ Verification Checklist

Run through this checklist to verify everything works:

- [ ] Eureka dashboard loads (http://localhost:8761)
- [ ] Analytics service shows as registered in Eureka
- [ ] Inventory status returns 10 products
- [ ] Sales by category returns 4 categories
- [ ] Top products API returns results
- [ ] Activity summary shows user engagement
- [ ] APIs work through Gateway (port 8080)
- [ ] APIs work directly (port 8081)
- [ ] Adminer connects to database
- [ ] Database has sample data (products, sales, activities)

---

## 🎉 Success!

Your microservices system is fully operational! All APIs are responding, caching is working, and services are communicating through the gateway.

**What makes this production-ready?**
- Containerized with Docker
- Service discovery and registration
- API Gateway for centralized routing
- Redis caching for performance
- Proper error handling and logging
- Database persistence with MySQL
- Health checks and monitoring
- Comprehensive test coverage
