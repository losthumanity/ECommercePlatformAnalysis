import React from 'react';
import {
  LineChart,
  Line,
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
import { TrendingUp } from 'lucide-react';

const DailySalesChart = ({ startDate, endDate }) => {
  const { data, loading, error } = useFetch(
    () => salesAPI.getDailySales(startDate, endDate),
    [startDate, endDate],
    30000 // Refresh every 30 seconds
  );

  if (loading) return <Loading message="Loading daily sales..." />;
  if (error) return <ErrorMessage message={error} />;
  if (!data || data.length === 0) {
    return (
      <Card title="Daily Sales Trend" icon={<TrendingUp size={20} />}>
        <div style={{ padding: '2rem', textAlign: 'center', color: '#6b7280' }}>
          No sales data available for the selected period
        </div>
      </Card>
    );
  }

  return (
    <Card title="Daily Sales Trend" icon={<TrendingUp size={20} />}>
      <ResponsiveContainer width="100%" height={300}>
        <LineChart data={data}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="date" />
          <YAxis />
          <Tooltip formatter={(value) => `$${value?.toFixed(2) || '0.00'}`} />
          <Legend />
          <Line
            type="monotone"
            dataKey="totalAmount"
            stroke="#3b82f6"
            strokeWidth={2}
            name="Sales Amount"
          />
        </LineChart>
      </ResponsiveContainer>
    </Card>
  );
};

export default DailySalesChart;
