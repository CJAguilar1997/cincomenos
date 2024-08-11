package com.tienda.cincomenos.domain.dto.factura;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;

public record FacturaDTO (
    Long id,
    @JsonAlias("fecha_emision")
    LocalDate fechaDeRegistro,
    @JsonAlias("importe_total")
    BigDecimal valorTotal
) {

}
