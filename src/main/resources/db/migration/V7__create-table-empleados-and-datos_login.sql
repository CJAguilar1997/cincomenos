CREATE TABLE Empleados(
    id_empleado BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(50) NOT NULL,
    tipo_empleado VARCHAR(30) NOT NULL,
    telefono VARCHAR(25) NOT NULL,
    email VARCHAR(50),
    fecha_registro DATE NOT NULL,
    usuario_activo BOOLEAN NOT NULL
);

CREATE TABLE Login_empleados(
    id_empleado BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);