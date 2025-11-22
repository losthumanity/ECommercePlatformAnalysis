import { useState, useEffect } from 'react';

/**
 * Custom hook for fetching data with loading and error states
 * @param {Function} fetchFunction - The API function to call
 * @param {Array} dependencies - Dependencies array for useEffect
 * @param {number} refreshInterval - Auto-refresh interval in milliseconds (0 = disabled)
 */
export const useFetch = (fetchFunction, dependencies = [], refreshInterval = 0) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let isMounted = true;
    let intervalId = null;

    const fetchData = async () => {
      try {
        setLoading(true);
        const result = await fetchFunction();
        if (isMounted) {
          setData(result);
          setError(null);
        }
      } catch (err) {
        if (isMounted) {
          setError(err.message || 'An error occurred');
          console.error('Fetch error:', err);
        }
      } finally {
        if (isMounted) {
          setLoading(false);
        }
      }
    };

    fetchData();

    // Set up auto-refresh if interval is specified
    if (refreshInterval > 0) {
      intervalId = setInterval(fetchData, refreshInterval);
    }

    return () => {
      isMounted = false;
      if (intervalId) {
        clearInterval(intervalId);
      }
    };
  }, dependencies);

  return { data, loading, error };
};

/**
 * Custom hook for managing date range state
 * @param {number} defaultDays - Default number of days to look back
 */
export const useDateRange = (defaultDays = 30) => {
  const [dateRange, setDateRange] = useState({
    startDate: new Date(Date.now() - defaultDays * 24 * 60 * 60 * 1000)
      .toISOString()
      .split('T')[0],
    endDate: new Date().toISOString().split('T')[0],
  });

  const updateDateRange = (startDate, endDate) => {
    setDateRange({ startDate, endDate });
  };

  return [dateRange, updateDateRange];
};
