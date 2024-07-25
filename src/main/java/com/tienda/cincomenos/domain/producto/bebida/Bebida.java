package com.tienda.cincomenos.domain.producto.bebida;

import org.springframework.format.annotation.DateTimeFormat;

import com.tienda.cincomenos.domain.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.Producto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Bebidas_detalle")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Bebida extends Producto {

    private String volumen;
    private String embase;
    private Boolean bebidaAlcoholica;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_vencimiento")
    private String fechaDeVencimiento;
    
    public Bebida(DatosRegistrarProducto datos) {
        super(datos);
        this.volumen = datos.atributosDeSubclases().getOrDefault("volumen", "");
        this.embase = datos.atributosDeSubclases().getOrDefault("embase", "");
        this.bebidaAlcoholica = Boolean.parseBoolean(datos.atributosDeSubclases().get("bebida_alcoholica"));
        this.fechaDeVencimiento = datos.atributosDeSubclases().get("fecha_vencimiento");
    }
}
