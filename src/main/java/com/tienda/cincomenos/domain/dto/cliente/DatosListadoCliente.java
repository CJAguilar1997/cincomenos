package com.tienda.cincomenos.domain.dto.cliente;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.tienda.cincomenos.domain.cliente.Cliente;

public record DatosListadoCliente(
    LocalDate fechaRegistro,
    String nombre,
    String telefono,
    BigDecimal totalCompras
) {

    public DatosListadoCliente(Cliente cliente) {
        this(cliente.getFechaRegistro(), cliente.getNombre(), cliente.getTelefono(), cliente.getTotalCompras());
    }
}
