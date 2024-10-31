package com.store.cincomenos.domain.dto.persona.empleado;

import com.store.cincomenos.domain.dto.persona.DatosRespuestaContacto;
import com.store.cincomenos.domain.persona.empleado.Empleado;

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
