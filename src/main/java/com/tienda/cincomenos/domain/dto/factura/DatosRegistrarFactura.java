package com.tienda.cincomenos.domain.dto.factura;

import java.math.BigDecimal;
import java.util.List;

public record DatosRegistrarFactura(
    List<Producto> items
) {
    public record Producto(
        String codigoDeBarras,
        String nombre,
        String marca,
        Integer cantidad,
        BigDecimal precioUnitario
    ) {

    }
}
