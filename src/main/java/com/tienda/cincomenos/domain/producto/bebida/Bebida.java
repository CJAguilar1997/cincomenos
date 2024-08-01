package com.tienda.cincomenos.domain.producto.bebida;

import java.util.HashMap;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.tienda.cincomenos.domain.producto.DatosActualizarProducto;
import com.tienda.cincomenos.domain.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.Producto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Bebidas_detalle")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
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
        this.fechaDeVencimiento = datos.atributosDeSubclases().getOrDefault("fecha_vencimiento", "");
    }

    @Override
    public Map<String, String> getAtributosSubclases() {
        Map<String, String> atributos = new HashMap<>();
        atributos.put("volumen", volumen);
        atributos.put("embase", embase);
        atributos.put("bebida_alcoholica", String.valueOf(bebidaAlcoholica));
        atributos.put("fecha_vencimiento", fechaDeVencimiento);
        return atributos;
    }

    public void actualizarAtributos(DatosActualizarProducto datos) {
        super.actualizarAtributos(datos);
        if (datos.atributosDeSubclases().get("volumen") != null) {
            this.volumen = datos.atributosDeSubclases().get("volumen");
        }
        if (datos.atributosDeSubclases().get("embase") != null) {
            this.embase = datos.atributosDeSubclases().get("embase");
        }
        if (datos.atributosDeSubclases().get("bebida_alcoholica") != null) {
            this.bebidaAlcoholica = Boolean.parseBoolean(datos.atributosDeSubclases().get("bebida_alcoholica"));
        }
        if (datos.atributosDeSubclases().get("fecha_vencimiento") != null) {
            this.fechaDeVencimiento = datos.atributosDeSubclases().get("fecha_vencimiento");
        }
    }
}