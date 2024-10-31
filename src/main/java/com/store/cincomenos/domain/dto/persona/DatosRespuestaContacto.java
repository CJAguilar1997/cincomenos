package com.store.cincomenos.domain.dto.persona;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.cincomenos.domain.persona.DatosDeContacto;


public record DatosRespuestaContacto(
    @JsonProperty(index = 0)
    String telefono,
    @JsonProperty(index = 1)
    String email
) {

    public DatosRespuestaContacto(DatosDeContacto contacto) {
        this(contacto.getTelefono(), contacto.getEmail());
    }

}
