INSERT INTO products(barcode, `name`, `description`, brand, price, stock, registration_date, active_product) 
    value ("6546798796", "Sustancia X", "Las super nenas papaaaaa", "Profe Utonio", 10.00, 20, "2012-10-08", 1);

INSERT INTO product_category(id_product, id_category) VALUE (1,1);

INSERT INTO product_attributes(id_product, id_attribute) VALUE (1,1);

INSERT INTO `values`(`value`) VALUE ("350g");

INSERT INTO attribute_value(attribute_id, value_id) VALUE (1,1);