INSERT INTO invoices_details (customer_id, issuance_date, total_amount) values (1, "2025-2-25", 500.00), (1, "2025-2-22", 3250.00);

INSERT INTO invoice_items (invoice_id, product_id, quantity, unit_price) values (1, 1, 10, 20.00), (1, 2, 10, 30.00);

INSERT INTO invoice_items (invoice_id, product_id, quantity, unit_price) values (2, 1, 320, 3250.00);