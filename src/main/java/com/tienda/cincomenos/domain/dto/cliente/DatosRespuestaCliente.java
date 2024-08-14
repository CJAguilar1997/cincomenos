package com.tienda.cincomenos.domain.dto.cliente;

import com.tienda.cincomenos.domain.cliente.Cliente;

public record DatosRespuestaCliente(
    Long id,
    String nombre,
    String dni,
    String email,
    String telefono
) {

    public DatosRespuestaCliente(Cliente cliente) {
        this(cliente.getId(), cliente.getNombre(), cliente.getDni(), cliente.getEmail(), cliente.getTelefono());
    }

}
