package com.tienda.cincomenos.domain.dto.persona.cliente;

public record ClienteDTO(
    Long id,
    String nombre,
    String dni,
    String telefono
) {

}
