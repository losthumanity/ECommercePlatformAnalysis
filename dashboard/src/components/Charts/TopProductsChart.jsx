import React from 'react';
import {
  PieChart,
  Pie,
  Cell,
  ResponsiveContainer,
  Tooltip,
  Legend,
} from 'recharts';
import Card from '../Card/Card';
import Loading from '../Loading/Loading';
import ErrorMessage from '../ErrorMessage/ErrorMessage';
import { useFetch } from '../../hooks/useFetch';
import { salesAPI } from '../../services/api';
import { Award } from 'lucide-react';

const COLORS = ['#3b82f6', '#8b5cf6', '#10b981', '#f59e0b', '#ef4444', '#06b6d4'];

const TopProductsChart = ({ startDate, endDate, limit = 5 }) => {
  const { data, loading, error } = useFetch(
    () => salesAPI.getTopProducts(startDate, endDate, limit),
    [startDate, endDate, limit],
    30000 // Refresh every 30 seconds
  );

  if (loading) return <Loading message="Loading top products..." />;
  if (error) return <ErrorMessage message={error} />;
  if (!data || data.length === 0) {
    return (
      <Card title="Top Selling Products" icon={<Award size={20} />}>
        <div style={{ padding: '2rem', textAlign: 'center', color: '#6b7280' }}>
          No sales data available for the selected period
        </div>
      </Card>
    );
  }

  return (
    <Card title="Top Selling Products" icon={<Award size={20} />}>
      <ResponsiveContainer width="100%" height={300}>
        <PieChart>
          <Pie
            data={data}
            dataKey="quantitySold"
            nameKey="productName"
            cx="50%"
            cy="50%"
            outerRadius={100}
            label={(entry) => `${entry.productName} (${entry.percentage?.toFixed(1) || 0}%)`}
          >
            {data?.map((entry, index) => (
              <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
            ))}
          </Pie>
          <Tooltip />
          <Legend />
        </PieChart>
      </ResponsiveContainer>
    </Card>
  );
};

export default TopProductsChart;
