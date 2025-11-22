import React from 'react';
import Card from '../Card/Card';
import Loading from '../Loading/Loading';
import ErrorMessage from '../ErrorMessage/ErrorMessage';
import { useFetch } from '../../hooks/useFetch';
import { inventoryAPI } from '../../services/api';
import { Package } from 'lucide-react';
import './InventoryStatus.css';

const InventoryStatus = () => {
  const { data, loading, error } = useFetch(
    () => inventoryAPI.getInventoryStatus(),
    [],
    60000 // Refresh every 60 seconds
  );

  if (loading) return <Loading message="Loading inventory..." />;
  if (error) return <ErrorMessage message={error} />;
  if (!data || data.length === 0) {
    return (
      <Card title="Inventory Status" icon={<Package size={20} />}>
        <div style={{ padding: '2rem', textAlign: 'center', color: '#6b7280' }}>
          No inventory data available
        </div>
      </Card>
    );
  }

  const getStatusColor = (status) => {
    switch (status) {
      case 'IN_STOCK':
        return 'status-success';
      case 'LOW_STOCK':
        return 'status-warning';
      case 'OUT_OF_STOCK':
        return 'status-danger';
      default:
        return '';
    }
  };

  return (
    <Card title="Inventory Status" icon={<Package size={20} />}>
      <div className="inventory-table-container">
        <table className="inventory-table">
          <thead>
            <tr>
              <th>Product</th>
              <th>Category</th>
              <th>Stock</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {data?.map((item) => (
              <tr key={item.id}>
                <td>{item.name}</td>
                <td>{item.category}</td>
                <td>{item.stockQuantity}</td>
                <td>
                  <span className={`status-badge ${getStatusColor(item.status)}`}>
                    {item.status.replace('_', ' ')}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </Card>
  );
};

export default InventoryStatus;
