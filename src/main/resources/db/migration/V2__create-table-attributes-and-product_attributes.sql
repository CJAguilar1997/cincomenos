CREATE TABLE attributes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE product_attributes(
    id_product BIGINT NOT NULL,
    id_attribute BIGINT NOT NULL,
    PRIMARY KEY(id_product, id_attribute)
)