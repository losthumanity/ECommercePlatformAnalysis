import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { BarChart3, Package, Users, Home } from 'lucide-react';
import './Navbar.css';

const Navbar = () => {
  const location = useLocation();

  const navItems = [
    { path: '/', icon: <Home size={20} />, label: 'Dashboard' },
    { path: '/sales', icon: <BarChart3 size={20} />, label: 'Sales' },
    { path: '/inventory', icon: <Package size={20} />, label: 'Inventory' },
    { path: '/activity', icon: <Users size={20} />, label: 'User Activity' },
  ];

  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <BarChart3 size={28} />
        <h1>E-Commerce Analytics</h1>
      </div>
      <ul className="navbar-menu">
        {navItems.map((item) => (
          <li key={item.path}>
            <Link
              to={item.path}
              className={`navbar-link ${location.pathname === item.path ? 'active' : ''}`}
            >
              {item.icon}
              <span>{item.label}</span>
            </Link>
          </li>
        ))}
      </ul>
    </nav>
  );
};

export default Navbar;
