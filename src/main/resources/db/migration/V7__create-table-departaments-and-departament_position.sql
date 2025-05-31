CREATE TABLE departaments(
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE `departament_position`(
    id_departament BIGINT NOT NULL,
    id_position BIGINT NOT NULL,
    PRIMARY KEY(id_departament, id_position)
);