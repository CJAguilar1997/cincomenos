package com.tienda.cincomenos.domain.dto.persona.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tienda.cincomenos.domain.dto.persona.DatosDeContactoDTO;
import com.tienda.cincomenos.domain.persona.cliente.Cliente;

@JsonPropertyOrder({"id", "nombre", "dni", "datos_contacto"})
public record DatosRespuestaCliente(
    Long id,
    String nombre,
    String dni,
    @JsonProperty("datos_contacto")
    DatosDeContactoDTO contacto
) {

    public DatosRespuestaCliente(Cliente cliente) {
        this(cliente.getId(), cliente.getNombre(), cliente.getDni(), new DatosDeContactoDTO(cliente.getContactoCliente().getTelefono(), cliente.getContactoCliente().getEmail()));
    }

}
