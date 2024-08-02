package com.tienda.cincomenos.domain.producto;

import java.time.LocalDate;
import java.util.Map;

import com.tienda.cincomenos.domain.producto.dto.DatosActualizarProducto;
import com.tienda.cincomenos.domain.producto.dto.DatosRegistrarProducto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "Productos")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_barras")
    private String codigoDeBarras;
    
    private String nombre;
    private String descripcion;
    private String marca;
    private Double precio;
    private Long stock;
    
    @Enumerated(EnumType.STRING)
    private CategoriaProducto categoria;
    
    @Column(name = "fecha_registro")
    private LocalDate fechaDeRegistro;

    private Boolean productoActivo;
    
    protected Producto(DatosRegistrarProducto datos) {
        this.codigoDeBarras = datos.codigoDeBarras();
        this.nombre = datos.nombre();
        this.descripcion = datos.descripcion();
        this.marca = datos.marca();
        this.precio = datos.precio();
        this.stock = datos.stock();
        this.categoria = datos.categoria();
        this.fechaDeRegistro = LocalDate.now();
        this.productoActivo = true;
    }

    public abstract Map<String, String> getAtributosSubclases();

    public void desactivarProducto() {
        this.productoActivo = false;
    }

    protected void actualizarAtributos(@Valid DatosActualizarProducto datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.descripcion() != null) {
            this.descripcion = datos.descripcion();
        }
        if (datos.marca() != null) {
            this.marca = datos.marca();
        }
        if (datos.precio() != null) {
            this.precio = datos.precio();
        }
        if (datos.categoria() != null) {
            this.categoria = datos.categoria();
        }
    }
}
