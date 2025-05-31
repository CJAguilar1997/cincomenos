CREATE TABLE attribute_value(
    attribute_id BIGINT NOT NULL,
    value_id BIGINT NOT NULL,
    PRIMARY KEY(attribute_id, value_id)
);