package com.tienda.cincomenos.domain.dto.persona.empleado;

import com.tienda.cincomenos.domain.dto.persona.DatosRespuestaContacto;
import com.tienda.cincomenos.domain.persona.empleado.Empleado;

public record DatosListadoEmpleado(
    Long id,
    String nombre,
    String dni,
    DatosRespuestaContacto contacto
) {

    public DatosListadoEmpleado(Empleado empleado) {
        this(empleado.getId(), empleado.getNombre(), empleado.getDni(), new DatosRespuestaContacto(empleado.getContacto()));
    }
}
