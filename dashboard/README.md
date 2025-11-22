# E-Commerce Analytics Dashboard

A modern React dashboard for visualizing real-time e-commerce analytics, including sales data, inventory status, and user activity metrics.

## 🎨 Features

- **Real-time Analytics**: Auto-refreshing data every 30-60 seconds
- **Interactive Charts**: Beautiful visualizations using Recharts library
- **Sales Analytics**:
  - Daily sales trends
  - Sales by category
  - Top selling products
  - Total sales metrics
- **Inventory Management**:
  - Real-time stock levels
  - Low stock alerts
  - Inventory status tracking
- **User Activity Tracking**:
  - Unique user counts
  - Most viewed products
  - Activity summaries
- **Responsive Design**: Works seamlessly on desktop and mobile devices

## 🚀 Quick Start

### Development Mode

```bash
cd dashboard
npm install
npm run dev
```

The dashboard will be available at `http://localhost:3000`

### Production Build

```bash
cd dashboard
npm run build
npm run preview
```

### Using Docker

```bash
# From project root
docker-compose up dashboard
```

The dashboard will be available at `http://localhost`

## 📦 Tech Stack

- **React 18** - UI library
- **Vite** - Build tool and dev server
- **React Router** - Navigation
- **Axios** - HTTP client
- **Recharts** - Chart visualization
- **Lucide React** - Icons
- **date-fns** - Date manipulation

## 🏗️ Project Structure

```
dashboard/
├── src/
│   ├── components/
│   │   ├── Card/           # Reusable card component
│   │   ├── Charts/         # Chart components
│   │   ├── Dashboard/      # Main dashboard view
│   │   ├── ErrorMessage/   # Error handling
│   │   ├── Loading/        # Loading states
│   │   ├── Navbar/         # Navigation bar
│   │   └── Stat/           # Statistics widgets
│   ├── hooks/
│   │   └── useFetch.js     # Custom data fetching hook
│   ├── services/
│   │   └── api.js          # API service layer
│   ├── App.jsx             # Main app component
│   ├── App.css
│   ├── index.css
│   └── main.jsx
├── Dockerfile
├── nginx.conf
├── package.json
└── vite.config.js
```

## 🔌 API Integration

The dashboard connects to the backend microservices via the API Gateway on port 8080:

- `/api/analytics/sales/*` - Sales analytics endpoints
- `/api/analytics/inventory/*` - Inventory data
- `/api/analytics/user-activity/*` - User activity metrics

## 🎯 Key Features

### Auto-Refresh
Data automatically refreshes at intervals:
- Sales data: Every 30 seconds
- Inventory: Every 60 seconds
- Manual refresh button available

### Real-time Updates
The dashboard uses React hooks to fetch and display real-time data from the backend APIs without page reloads.

### Error Handling
Comprehensive error handling with user-friendly error messages and retry functionality.

### Responsive Design
Fully responsive layout that adapts to different screen sizes and devices.

## 🛠️ Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint

## 🐳 Docker Deployment

The dashboard is containerized and can be deployed using Docker:

1. **Build Stage**: Uses Node.js to build the React application
2. **Production Stage**: Serves the built files using Nginx

The Nginx configuration includes:
- API proxy to backend services
- Static file caching
- Gzip compression
- SPA routing support

## 📊 Dashboard Sections

1. **Overview Stats**: Key metrics at a glance
2. **Daily Sales Chart**: Line chart showing sales trends
3. **Sales by Category**: Bar chart of category performance
4. **Top Products**: Pie chart of best-selling items
5. **Inventory Table**: Real-time stock levels

## 🔧 Configuration

The dashboard can be configured via `vite.config.js`:

```javascript
export default defineConfig({
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    }
  }
})
```

## 📝 Notes

- Ensure the backend services are running before starting the dashboard
- The API Gateway must be accessible on port 8080
- For production deployment, update the API proxy configuration in nginx.conf
