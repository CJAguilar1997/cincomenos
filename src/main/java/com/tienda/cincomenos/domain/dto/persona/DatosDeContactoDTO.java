package com.tienda.cincomenos.domain.dto.persona;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tienda.cincomenos.domain.persona.cliente.Cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@JsonPropertyOrder({"telefono", "email"})
public record DatosDeContactoDTO(
    @NotBlank
    @Pattern(regexp = "\\+?[0-9 ]+", message = "El números contiene simbolos invalidos")
    String telefono,

    @Email
    String email
) {

    public DatosDeContactoDTO(Cliente cliente) {
        this(cliente.getContactoCliente().getTelefono(), cliente.getContactoCliente().getEmail());
    }

}