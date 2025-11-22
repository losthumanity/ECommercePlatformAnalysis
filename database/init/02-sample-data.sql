-- Insert sample data for testing

-- Sample Products
INSERT INTO products (name, category, price, stock_quantity) VALUES
('Laptop Pro 15', 'Electronics', 1299.99, 50),
('Wireless Mouse', 'Electronics', 29.99, 200),
('Office Chair', 'Furniture', 249.99, 75),
('Standing Desk', 'Furniture', 599.99, 30),
('Coffee Maker', 'Appliances', 89.99, 120),
('Water Bottle', 'Accessories', 19.99, 300),
('Backpack', 'Accessories', 49.99, 150),
('Monitor 27"', 'Electronics', 349.99, 60),
('Desk Lamp', 'Furniture', 39.99, 100),
('USB-C Cable', 'Electronics', 12.99, 500);

-- Sample Sales
INSERT INTO sales (product_id, quantity, total_amount, sale_date, customer_id, status) VALUES
(1, 2, 2599.98, DATE_SUB(NOW(), INTERVAL 1 DAY), 101, 'COMPLETED'),
(2, 5, 149.95, DATE_SUB(NOW(), INTERVAL 2 DAY), 102, 'COMPLETED'),
(3, 1, 249.99, DATE_SUB(NOW(), INTERVAL 3 DAY), 103, 'COMPLETED'),
(4, 1, 599.99, DATE_SUB(NOW(), INTERVAL 4 DAY), 104, 'COMPLETED'),
(5, 3, 269.97, DATE_SUB(NOW(), INTERVAL 5 DAY), 105, 'COMPLETED'),
(6, 10, 199.90, DATE_SUB(NOW(), INTERVAL 6 DAY), 106, 'COMPLETED'),
(7, 2, 99.98, DATE_SUB(NOW(), INTERVAL 7 DAY), 107, 'COMPLETED'),
(8, 1, 349.99, DATE_SUB(NOW(), INTERVAL 1 HOUR), 108, 'COMPLETED'),
(1, 1, 1299.99, DATE_SUB(NOW(), INTERVAL 2 HOUR), 109, 'PENDING'),
(9, 4, 159.96, DATE_SUB(NOW(), INTERVAL 3 HOUR), 110, 'COMPLETED');

-- Sample User Activities
INSERT INTO user_activities (user_id, activity_type, product_id, activity_timestamp, ip_address) VALUES
(101, 'VIEW', 1, DATE_SUB(NOW(), INTERVAL 1 DAY), '192.168.1.100'),
(101, 'ADD_TO_CART', 1, DATE_SUB(NOW(), INTERVAL 1 DAY), '192.168.1.100'),
(102, 'VIEW', 2, DATE_SUB(NOW(), INTERVAL 2 DAY), '192.168.1.101'),
(102, 'PURCHASE', 2, DATE_SUB(NOW(), INTERVAL 2 DAY), '192.168.1.101'),
(103, 'VIEW', 3, DATE_SUB(NOW(), INTERVAL 3 DAY), '192.168.1.102'),
(103, 'ADD_TO_CART', 3, DATE_SUB(NOW(), INTERVAL 3 DAY), '192.168.1.102'),
(104, 'SEARCH', NULL, DATE_SUB(NOW(), INTERVAL 4 DAY), '192.168.1.103'),
(105, 'VIEW', 5, DATE_SUB(NOW(), INTERVAL 5 DAY), '192.168.1.104'),
(106, 'VIEW', 6, DATE_SUB(NOW(), INTERVAL 6 DAY), '192.168.1.105'),
(107, 'PURCHASE', 7, DATE_SUB(NOW(), INTERVAL 7 DAY), '192.168.1.106');
