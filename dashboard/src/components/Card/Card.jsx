import React from 'react';
import './Card.css';

const Card = ({ title, children, icon, className = '' }) => {
  return (
    <div className={`card ${className}`}>
      <div className="card-header">
        {icon && <span className="card-icon">{icon}</span>}
        <h3 className="card-title">{title}</h3>
      </div>
      <div className="card-content">{children}</div>
    </div>
  );
};

export default Card;
