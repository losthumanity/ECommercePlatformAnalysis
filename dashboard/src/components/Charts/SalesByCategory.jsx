import React from 'react';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts';
import Card from '../Card/Card';
import Loading from '../Loading/Loading';
import ErrorMessage from '../ErrorMessage/ErrorMessage';
import { useFetch } from '../../hooks/useFetch';
import { salesAPI } from '../../services/api';
import { BarChart3 } from 'lucide-react';

const SalesByCategory = ({ startDate, endDate }) => {
  const { data, loading, error } = useFetch(
    () => salesAPI.getSalesByCategory(startDate, endDate),
    [startDate, endDate],
    30000 // Refresh every 30 seconds
  );

  if (loading) return <Loading message="Loading sales data..." />;
  if (error) return <ErrorMessage message={error} />;
  if (!data || data.length === 0) {
    return (
      <Card title="Sales by Category" icon={<BarChart3 size={20} />}>
        <div style={{ padding: '2rem', textAlign: 'center', color: '#6b7280' }}>
          No sales data available for the selected period
        </div>
      </Card>
    );
  }

  return (
    <Card title="Sales by Category" icon={<BarChart3 size={20} />}>
      <ResponsiveContainer width="100%" height={300}>
        <BarChart data={data}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="category" />
          <YAxis />
          <Tooltip formatter={(value) => `$${value?.toFixed(2) || '0.00'}`} />
          <Legend />
          <Bar dataKey="totalSales" fill="#3b82f6" name="Total Sales" />
        </BarChart>
      </ResponsiveContainer>
    </Card>
  );
};

export default SalesByCategory;
