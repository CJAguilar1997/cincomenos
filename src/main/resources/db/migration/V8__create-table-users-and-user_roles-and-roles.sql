CREATE TABLE users(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE user_roles(
    id_user BIGINT NOT NULL,
    id_role BIGINT NOT NULL
);
 
CREATE TABLE roles(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    role VARCHAR(20) NOT NULL UNIQUE
);