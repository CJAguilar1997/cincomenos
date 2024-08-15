package com.tienda.cincomenos.domain.cliente;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.tienda.cincomenos.domain.dto.cliente.DatosRegistrarCliente;

import jakarta.persistence.Column;
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
@EqualsAndHashCode
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fecha_registro")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fechaRegistro;
    
    private String nombre;
    private String dni;
    private String email;
    private String telefono;
    
    @Column(name = "usuario_activo")
    private Boolean usuarioActivo;

    public Cliente(DatosRegistrarCliente datos) {
        this.fechaRegistro = LocalDate.now();
        this.nombre = datos.nombre();
        this.dni = datos.dni();
        this.email = datos.email();
        this.telefono = datos.telefono();
        this.usuarioActivo = true;
    }

    public void actualizarDatos(Cliente cliente) {
        if (cliente.getNombre() != null || !cliente.getNombre().isEmpty()) {
            this.nombre = cliente.getNombre();
        }
        if (cliente.getDni() != null || !cliente.getDni().isEmpty()) {
            this.dni = cliente.getDni();
        }
        if (cliente.getEmail() != null) {
            this.email = cliente.getEmail();
        }
        if (cliente.getTelefono() != null || !cliente.getTelefono().isEmpty()) {
            this.telefono = cliente.getTelefono();
        }
    }

    public void desactivarCliente() {
        this.usuarioActivo = false;
    }
}
