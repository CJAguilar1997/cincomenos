CREATE TABLE categories(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE product_category(
    id_product BIGINT NOT NULL,
    id_category BIGINT NOT NULL,
    PRIMARY KEY(id_product, id_category)
)