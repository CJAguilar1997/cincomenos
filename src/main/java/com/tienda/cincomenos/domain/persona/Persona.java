package com.tienda.cincomenos.domain.persona;

import java.util.Map;

import com.tienda.cincomenos.domain.dto.cliente.DatosActualizar;
import com.tienda.cincomenos.domain.dto.cliente.DatosRegistrarCliente;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Persona {

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "dni")
    private String dni;
    @Column(name = "usuario_activo")
    private Boolean usuarioActivo;
    
    protected Persona(DatosRegistrarCliente datos) {
        this.nombre = datos.nombre();
        this.dni = datos.dni();
        this.usuarioActivo = true;
    }

    protected void actualizarAtributosCliente(DatosActualizar datos) {
        Map<String, Object> atributos = datos.getAtributos();
        atributos.forEach((key, value) -> {
            switch (key) {
                case "nombre":
                    this.nombre = (String) value;
                    break;
                case "dni":
                    this.dni = (String) value;
                    break;
                default:
                    break;
            }
        });
    }

    public void desactivarCuenta() {
        this.usuarioActivo = false;
    }
}
