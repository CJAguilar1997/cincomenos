package com.tienda.cincomenos.domain.persona.empleado;

import com.tienda.cincomenos.domain.dto.persona.empleado.DatosActualizarEmpleado;
import com.tienda.cincomenos.domain.dto.persona.empleado.DatosRegistrarEmpleado;
import com.tienda.cincomenos.domain.persona.DatosDeContacto;
import com.tienda.cincomenos.domain.persona.Persona;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "Empleados")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Empleado extends Persona{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "puesto_trabajo")
    PuestoTrabajo puestoTrabajo;

    @Embedded
    DatosDeContacto contacto;

    public Empleado (DatosRegistrarEmpleado datos) {
        super(datos);
        this.puestoTrabajo = datos.puestoTrabajo();
        this.contacto = new DatosDeContacto(datos.contacto().telefono(), datos.contacto().email());
    }

    public void actualizarDatos(DatosActualizarEmpleado datos) {
        if (datos.contacto() != null){
            if (datos.contacto().email() != null && !datos.contacto().email().isBlank()) {
                this.contacto.setEmail(datos.contacto().email());
            }
            if (datos.contacto().telefono() != null && !datos.contacto().telefono().isBlank()) {
                this.contacto.setTelefono(datos.contacto().telefono());
            }
        }
    }

    public void borrarCuentaEmpleado() {
        super.desactivarCuenta();
    }
}
