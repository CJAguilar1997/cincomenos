package com.store.cincomenos.domain.dto.invoice;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemsFacturaDTO (
    Long id,
    Integer cantidad,
    @JsonProperty("precio_unitario")
    BigDecimal precioUnitario,
    @JsonProperty("precio_valor_cantidad")
    BigDecimal precioValorCantidad,
    ProductoDTO producto
) {
    

}
