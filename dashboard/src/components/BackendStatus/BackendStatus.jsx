import React, { useState, useEffect } from 'react';
import './BackendStatus.css';

const BackendStatus = () => {
  const [status, setStatus] = useState('checking');

  useEffect(() => {
    const checkBackend = async () => {
      try {
        const response = await fetch('/api/analytics/inventory/status');
        if (response.ok) {
          setStatus('online');
        } else {
          setStatus('offline');
        }
      } catch (error) {
        setStatus('offline');
      }
    };

    checkBackend();
    const interval = setInterval(checkBackend, 10000);
    return () => clearInterval(interval);
  }, []);

  if (status === 'online') return null;

  return (
    <div className="backend-status-banner">
      <div className="backend-status-content">
        {status === 'checking' ? (
          <>
            <span className="status-spinner"></span>
            <span>Connecting to backend services...</span>
          </>
        ) : (
          <>
            <span className="status-icon">⚠️</span>
            <div>
              <strong>Backend Services Not Available</strong>
              <p>The dashboard is ready, but backend services are not running. Start them with: <code>docker-compose up</code></p>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default BackendStatus;
