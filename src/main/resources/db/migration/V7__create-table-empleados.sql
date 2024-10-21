CREATE TABLE Empleados(
    id_empleado BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(50) NOT NULL UNIQUE,
    puesto_trabajo VARCHAR(30) NOT NULL,
    telefono VARCHAR(25) NOT NULL UNIQUE,
    email VARCHAR(50) UNIQUE,
    fecha_registro DATE NOT NULL,
    usuario_activo BOOLEAN NOT NULL
);
