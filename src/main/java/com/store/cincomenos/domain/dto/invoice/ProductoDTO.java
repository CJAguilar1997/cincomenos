package com.store.cincomenos.domain.dto.invoice;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductoDTO (
    @JsonProperty("codigo_barras")
    String codigoDeBarras,
    String nombre, 
    String descripcion, 
    String marca
) {
    
}
