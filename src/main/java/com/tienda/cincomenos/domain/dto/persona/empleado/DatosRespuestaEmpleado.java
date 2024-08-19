package com.tienda.cincomenos.domain.dto.persona.empleado;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tienda.cincomenos.domain.dto.persona.DatosRespuestaContacto;
import com.tienda.cincomenos.domain.persona.empleado.Empleado;
import com.tienda.cincomenos.domain.persona.empleado.TipoEmpleado;

@JsonPropertyOrder({"nombre", "dni", "tipo_empleado", "datos_contacto"})
public record DatosRespuestaEmpleado(
    String nombre,
    String dni,

    @JsonProperty("tipo_empleado")
    TipoEmpleado tipoEmpleado,
    
    @JsonProperty("datos_contacto")
    DatosRespuestaContacto contacto
) {

    public DatosRespuestaEmpleado(Empleado empleado) {
        this(empleado.getNombre(), empleado.getDni(), empleado.getTipoEmpleado(), new DatosRespuestaContacto(empleado.getContacto()));
    }

}
