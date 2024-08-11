CREATE TABLE Facturas_Detalle (
  id_factura BIGINT PRIMARY KEY AUTO_INCREMENT,
  fecha_emision DATE NOT NULL,
  importe_total DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Items_Facturas (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  factura_id BIGINT NOT NULL,
  producto_id INT NOT NULL,
  cantidad INT NOT NULL,
  precio_unitario DECIMAL(10, 2) NOT NULL
);
