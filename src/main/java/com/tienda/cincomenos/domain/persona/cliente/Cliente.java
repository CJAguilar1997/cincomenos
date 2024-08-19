package com.tienda.cincomenos.domain.persona.cliente;

import com.tienda.cincomenos.domain.dto.cliente.DatosActualizarCliente;
import com.tienda.cincomenos.domain.dto.cliente.DatosRegistrarCliente;
import com.tienda.cincomenos.domain.persona.DatosDeContacto;
import com.tienda.cincomenos.domain.persona.Persona;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Clientes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Persona{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DatosDeContacto contactoCliente;

    public Cliente(DatosRegistrarCliente datos) {
        super(datos);
        this.contactoCliente = new DatosDeContacto(datos.contacto().telefono(), datos.contacto().email());
    }

    public void actualizarDatos(DatosActualizarCliente datos) {
        super.actualizarAtributosCliente(datos);
        if (datos.contacto() != null) {
            if (datos.contacto().email() != null && !datos.contacto().email().isBlank()) {
                this.contactoCliente.setEmail(datos.contacto().email());
            }
            if (datos.contacto().telefono() != null && !datos.contacto().telefono().isBlank()) {
                this.contactoCliente.setTelefono(datos.contacto().telefono());
            }
        }
        
    }

    public void desactivarCliente() {
        super.desactivarCuenta();
    }
}
