CREATE SCHEMA ecommerce;
USE ecommerce;
CREATE TABLE User (
    id INTEGER PRIMARY KEY,
    name VARCHAR(20),
    email VARCHAR(50),
    phone VARCHAR(20),
    address VARCHAR(200)
);

CREATE TABLE Order_info (
    order_id INT PRIMARY KEY,
    user_id INT,
    item_name VARCHAR(255),
    quantity INT,
    order_date DATE
);

INSERT INTO User (id, name, email, phone, address)
VALUES (1, 'Abhisha Thaker', 'ab@example.com', '1234567890', '123 Summer Street');

INSERT INTO User (id, name, email, phone, address)
VALUES (2, 'Nisha Patel', 'ad@example.com', '9876543210', '456 Spring Avenue');

select * from Order_info;

