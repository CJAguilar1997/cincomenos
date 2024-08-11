package com.tienda.cincomenos.domain.dto.factura;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosRegistrarFactura(
    List<Producto> items
) {
    public record Producto(
        @JsonAlias("codigo_barras")
        String codigoDeBarras,
        String nombre,
        String marca,
        Integer cantidad,
        @JsonAlias("precio_unitario")
        BigDecimal precioUnitario
    ) {

    }
}
