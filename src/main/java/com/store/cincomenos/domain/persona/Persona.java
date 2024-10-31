package com.store.cincomenos.domain.persona;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.store.cincomenos.domain.dto.persona.DatosActualizar;

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

    @Column(name = "fecha_registro")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fechaRegistro;
    
    protected <T extends DatosRegistrar> Persona(T datos) {
        this.nombre = datos.nombre();
        this.dni = datos.dni();
        this.fechaRegistro = LocalDate.now();
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

    protected void desactivarCuenta() {
        this.usuarioActivo = false;
    }

}
