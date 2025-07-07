CREATE TABLE invoices_details (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  customer_id BIGINT NOT NULL,
  issuance_date DATE NOT NULL,
  total_amount DECIMAL(10, 2) NOT NULL
);

CREATE TABLE invoice_items (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  invoice_id BIGINT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  unit_price DECIMAL(10, 2) NOT NULL
);
