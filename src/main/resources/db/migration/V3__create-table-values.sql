CREATE TABLE `values`(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    value VARCHAR(50) NOT NULL
);

CREATE TABLE product_attributes_values(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    attribute_id BIGINT NOT NULL,
    value_id BIGINT NOT NULL,
    CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_attribute_id FOREIGN KEY (attribute_id) REFERENCES attributes(id),
    CONSTRAINT fk_value_id FOREIGN KEY (value_id) REFERENCES `values`(id)
);