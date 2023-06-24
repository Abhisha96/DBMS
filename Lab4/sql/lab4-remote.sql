CREATE SCHEMA INVENTORY;
use INVENTORY;

CREATE TABLE Inventory (
    item_id INT PRIMARY KEY,
    item_name VARCHAR(255),
    available_quantity INT
);


INSERT INTO Inventory (item_id, item_name, available_quantity)
VALUES (1, 'Item 1', 10);
-- Insert multiple rows using a single INSERT statement
INSERT INTO Inventory (item_id, item_name, available_quantity)
VALUES (2, 'Item 2', 5);
select * from Inventory;