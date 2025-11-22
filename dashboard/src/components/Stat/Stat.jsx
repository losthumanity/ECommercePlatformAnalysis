import React from 'react';
import './Stat.css';

const Stat = ({ label, value, icon, trend, color = 'primary' }) => {
  return (
    <div className={`stat stat-${color}`}>
      {icon && <div className="stat-icon">{icon}</div>}
      <div className="stat-details">
        <div className="stat-label">{label}</div>
        <div className="stat-value">{value}</div>
        {trend && (
          <div className={`stat-trend ${trend > 0 ? 'positive' : 'negative'}`}>
            {trend > 0 ? '↑' : '↓'} {Math.abs(trend)}%
          </div>
        )}
      </div>
    </div>
  );
};

export default Stat;
