package com.tienda.cincomenos.domain.dto.factura;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FacturaDTO (
    Long id,
    @JsonProperty("fecha_emision")
    LocalDate fechaDeRegistro,
    @JsonProperty("importe_total")
    BigDecimal valorTotal
) {

}
