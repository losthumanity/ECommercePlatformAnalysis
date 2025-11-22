import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar/Navbar';
import Dashboard from './components/Dashboard/Dashboard';
import BackendStatus from './components/BackendStatus/BackendStatus';
import './App.css';

function App() {
  return (
    <Router>
      <div className="app">
        <Navbar />
        <BackendStatus />
        <main className="main-content">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/sales" element={<Dashboard />} />
            <Route path="/inventory" element={<Dashboard />} />
            <Route path="/activity" element={<Dashboard />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
