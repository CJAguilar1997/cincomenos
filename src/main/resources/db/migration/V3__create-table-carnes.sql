CREATE TABLE Carnes_detalle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    peso VARCHAR(20) NOT NULL,
    venta_unidad BOOLEAN NOT NULL,
    empaque VARCHAR(20),
    tipo_carne VARCHAR(20)
);