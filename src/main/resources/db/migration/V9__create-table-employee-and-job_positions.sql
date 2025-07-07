CREATE TABLE employees(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    dni VARCHAR(50) NOT NULL UNIQUE,
    phone_number VARCHAR(25) NOT NULL UNIQUE,
    email VARCHAR(50) UNIQUE,
    address VARCHAR(100) NOT NULL,
    registration_date DATE NOT NULL,
    `date_of_dismissal/resignation` DATE DEFAULT NULL,
    active_user BOOLEAN NOT NULL
);

CREATE TABLE `job_positions`(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE employee_departaments_positions(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_employee BIGINT NOT NULL,
    id_departament BIGINT NOT NULL,
    id_position BIGINT NOT NULL,
    CONSTRAINT fk_employee_id FOREIGN KEY (id_employee) REFERENCES employees(id),
    CONSTRAINT fk_departament_id FOREIGN KEY (id_departament) REFERENCES departaments(id),
    CONSTRAINT fk_positions_id FOREIGN KEY (id_position) REFERENCES job_positions(id)
);

