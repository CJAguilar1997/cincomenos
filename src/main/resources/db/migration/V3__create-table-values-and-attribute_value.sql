CREATE TABLE `values`(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    value VARCHAR(50) NOT NULL
);

CREATE TABLE attribute_value(
    id_attribute BIGINT NOT NULL,
    id_value BIGINT NOT NULL,
    PRIMARY KEY(id_attribute, id_value)
)