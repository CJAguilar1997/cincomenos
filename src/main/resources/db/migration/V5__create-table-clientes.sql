CREATE TABLE Clientes(
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    fecha_registro DATE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    telefono VARCHAR(25) NOT NULL,
    usuario_activo BOOLEAN NOT NULL
)