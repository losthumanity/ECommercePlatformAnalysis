import axios from 'axios';
import { format, subDays } from 'date-fns';

const API_BASE_URL = '/api/analytics';

// Create axios instance with default config
const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add request interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error);
    return Promise.reject(error);
  }
);

// Helper function to format dates
const formatDate = (date) => format(date, 'yyyy-MM-dd');

// Default date range: last 30 days
const getDefaultDateRange = () => ({
  startDate: formatDate(subDays(new Date(), 30)),
  endDate: formatDate(new Date()),
});

// Sales Analytics API
export const salesAPI = {
  getSalesByCategory: async (startDate, endDate) => {
    const params = startDate && endDate
      ? { startDate, endDate }
      : getDefaultDateRange();
    const response = await api.get('/sales/by-category', { params });
    return response.data;
  },

  getTopProducts: async (startDate, endDate, limit = 10) => {
    const params = startDate && endDate
      ? { startDate, endDate, limit }
      : { ...getDefaultDateRange(), limit };
    const response = await api.get('/sales/top-products', { params });
    return response.data;
  },

  getDailySales: async (startDate, endDate) => {
    const params = startDate && endDate
      ? { startDate, endDate }
      : getDefaultDateRange();
    const response = await api.get('/sales/daily', { params });
    return response.data;
  },

  getTotalSales: async (startDate, endDate) => {
    const params = startDate && endDate
      ? { startDate, endDate }
      : getDefaultDateRange();
    const response = await api.get('/sales/total', { params });
    return response.data;
  },
};

// Inventory Analytics API
export const inventoryAPI = {
  getInventoryStatus: async () => {
    const response = await api.get('/inventory/status');
    return response.data;
  },

  getLowStockProducts: async (threshold = 50) => {
    const response = await api.get('/inventory/low-stock', {
      params: { threshold },
    });
    return response.data;
  },
};

// User Activity Analytics API
export const userActivityAPI = {
  getActivitySummary: async (startDate, endDate) => {
    const params = startDate && endDate
      ? { startDate, endDate }
      : getDefaultDateRange();
    const response = await api.get('/user-activity/summary', { params });
    return response.data;
  },

  getMostViewedProducts: async (startDate, endDate, limit = 10) => {
    const params = startDate && endDate
      ? { startDate, endDate, limit }
      : { ...getDefaultDateRange(), limit };
    const response = await api.get('/user-activity/most-viewed', { params });
    return response.data;
  },

  getUniqueUsersCount: async (startDate, endDate) => {
    const params = startDate && endDate
      ? { startDate, endDate }
      : getDefaultDateRange();
    const response = await api.get('/user-activity/unique-users', { params });
    return response.data;
  },
};

export default api;
