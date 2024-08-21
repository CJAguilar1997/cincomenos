package com.tienda.cincomenos.domain.dto.persona.cliente;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tienda.cincomenos.domain.dto.persona.DatosDeContactoDTO;
import com.tienda.cincomenos.domain.persona.cliente.Cliente;

@JsonPropertyOrder({"fecha_registro", "nombre", "datos_contacto"})
public record DatosListadoCliente(
    
    @JsonProperty("fecha_registro")
    LocalDate fechaRegistro,

    String nombre,

    @JsonProperty("datos_contacto")
    DatosDeContactoDTO contacto
) {

    public DatosListadoCliente(Cliente cliente) {
        this(cliente.getFechaRegistro(), cliente.getNombre(), new DatosDeContactoDTO(cliente.getContactoCliente().getTelefono(), cliente.getContactoCliente().getEmail()));
    }
}
