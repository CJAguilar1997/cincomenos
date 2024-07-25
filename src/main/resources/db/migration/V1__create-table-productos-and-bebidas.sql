CREATE TABLE Productos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    codigo_barras VARCHAR(50),
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    marca VARCHAR(50),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    categoria VARCHAR(40),
    fecha_registro DATE,
    producto_activo BOOLEAN
);

CREATE TABLE Bebidas_detalle (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    volumen VARCHAR(10),
    embase VARCHAR(20),
    bebida_alcoholica BOOLEAN,
    fecha_vencimiento DATE
);
