import React from 'react';
import { useFetch } from '../../hooks/useFetch';
import { salesAPI, inventoryAPI, userActivityAPI } from '../../services/api';
import Stat from '../Stat/Stat';
import SalesByCategory from '../Charts/SalesByCategory';
import DailySalesChart from '../Charts/DailySalesChart';
import TopProductsChart from '../Charts/TopProductsChart';
import InventoryStatus from '../Charts/InventoryStatus';
import { DollarSign, Package, Users, TrendingUp, RefreshCw } from 'lucide-react';
import './Dashboard.css';

const Dashboard = () => {
  const [refreshKey, setRefreshKey] = React.useState(0);
  const [dateRange] = React.useState({
    startDate: new Date(Date.now() - 30 * 24 * 60 * 60 * 1000).toISOString().split('T')[0],
    endDate: new Date().toISOString().split('T')[0],
  });

  const { data: totalSales, loading: salesLoading, error: salesError } = useFetch(
    () => salesAPI.getTotalSales(dateRange.startDate, dateRange.endDate),
    [dateRange.startDate, dateRange.endDate, refreshKey],
    30000
  );

  const { data: lowStock, loading: inventoryLoading, error: inventoryError } = useFetch(
    () => inventoryAPI.getLowStockProducts(50),
    [refreshKey],
    60000
  );

  const { data: uniqueUsers, loading: usersLoading, error: usersError } = useFetch(
    () => userActivityAPI.getUniqueUsersCount(dateRange.startDate, dateRange.endDate),
    [dateRange.startDate, dateRange.endDate, refreshKey],
    30000
  );

  const handleRefresh = () => {
    setRefreshKey((prev) => prev + 1);
  };

  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <div>
          <h2 className="dashboard-title">Analytics Dashboard</h2>
          <p className="dashboard-subtitle">Real-time e-commerce insights (Last 30 days)</p>
        </div>
        <button className="refresh-button" onClick={handleRefresh}>
          <RefreshCw size={18} />
          Refresh
        </button>
      </div>

      <div className="stats-grid">
        <Stat
          label="Total Sales"
          value={salesLoading ? '...' : `$${totalSales?.toFixed(2) || '0.00'}`}
          icon={<DollarSign size={24} />}
          color="primary"
        />
        <Stat
          label="Low Stock Items"
          value={inventoryLoading ? '...' : lowStock?.length || 0}
          icon={<Package size={24} />}
          color="warning"
        />
        <Stat
          label="Unique Users"
          value={usersLoading ? '...' : uniqueUsers || 0}
          icon={<Users size={24} />}
          color="success"
        />
        <Stat
          label="Avg. Daily Sales"
          value={
            salesLoading
              ? '...'
              : `$${totalSales ? (totalSales / 30).toFixed(2) : '0.00'}`
          }
          icon={<TrendingUp size={24} />}
          color="primary"
        />
      </div>

      <div className="charts-grid">
        <div className="chart-item-wide">
          <DailySalesChart startDate={dateRange.startDate} endDate={dateRange.endDate} />
        </div>
        <div className="chart-item">
          <SalesByCategory startDate={dateRange.startDate} endDate={dateRange.endDate} />
        </div>
        <div className="chart-item">
          <TopProductsChart
            startDate={dateRange.startDate}
            endDate={dateRange.endDate}
            limit={5}
          />
        </div>
        <div className="chart-item-wide">
          <InventoryStatus />
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
