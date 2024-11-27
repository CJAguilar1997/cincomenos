CREATE TABLE employees(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    dni VARCHAR(50) NOT NULL UNIQUE,
    phone_number VARCHAR(25) NOT NULL UNIQUE,
    email VARCHAR(50) UNIQUE,
    address VARCHAR(100) NOT NULL,
    id_job_position BIGINT NOT NULL,
    registration_date DATE NOT NULL,
    `date_of_dismissal/resignation` DATE DEFAULT NULL,
    active_user BOOLEAN NOT NULL
);

CREATE TABLE `job_positions`(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
)
