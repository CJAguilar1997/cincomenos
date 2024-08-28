CREATE TABLE Empleados(
    id_empleado BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(50) NOT NULL,
    puesto_trabajo VARCHAR(30) NOT NULL,
    telefono VARCHAR(25) NOT NULL,
    email VARCHAR(50),
    fecha_registro DATE NOT NULL,
    usuario_activo BOOLEAN NOT NULL
);
