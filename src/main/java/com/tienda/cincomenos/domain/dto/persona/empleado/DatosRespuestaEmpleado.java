package com.tienda.cincomenos.domain.dto.persona.empleado;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tienda.cincomenos.domain.dto.persona.DatosRespuestaContacto;
import com.tienda.cincomenos.domain.persona.empleado.Empleado;
import com.tienda.cincomenos.domain.persona.empleado.PuestoTrabajo;

@JsonPropertyOrder({"id", "nombre", "dni", "tipo_empleado", "datos_contacto"})
public record DatosRespuestaEmpleado(
    Long id,
    String nombre,
    String dni,

    @JsonProperty("tipo_empleado")
    PuestoTrabajo tipoEmpleado,
    
    @JsonProperty("datos_contacto")
    DatosRespuestaContacto contacto
) {

    public DatosRespuestaEmpleado(Empleado empleado) {
        this(empleado.getId(), empleado.getNombre(), empleado.getDni(), empleado.getPuestoTrabajo(), new DatosRespuestaContacto(empleado.getContacto()));
    }

}
