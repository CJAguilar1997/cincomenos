package com.store.cincomenos.domain.producto.carne;

import java.util.HashMap;
import java.util.Map;

import com.store.cincomenos.domain.dto.producto.DatosActualizarProducto;
import com.store.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.store.cincomenos.domain.producto.productoBase.Producto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Carnes_detalle")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Carne extends Producto {

    private String peso;
    
    @Column(name = "venta_unidad")
    private Boolean ventaPorUnidad;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "empaque")
    private TipoEmpaque empaque;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_carne")
    private TipoCarne tipo;

    public Carne(DatosRegistrarProducto datos) {
        super(datos);
        this.peso = datos.atributosDeSubclases().get("peso");
        this.ventaPorUnidad = Boolean.parseBoolean(datos.atributosDeSubclases().get("venta_unidad"));
        this.empaque = TipoEmpaque.valueOf(datos.atributosDeSubclases().get("empaque"));
        this.tipo = TipoCarne.valueOf(datos.atributosDeSubclases().get("tipo_carne"));
    }

    @Override
    public Map<String, String> getAtributosSubclases() {
        Map<String, String> atributos = new HashMap<>();
        atributos.put("peso", peso);
        atributos.put("venta_unidad", String.valueOf(ventaPorUnidad));
        atributos.put("empaque", String.valueOf(empaque));
        atributos.put("tipo", String.valueOf(tipo));
        return atributos;
    }
    
    public void actualizarAtributos(DatosActualizarProducto datos) {
        super.actualizarAtributos(datos);
        if (datos.atributosDeSubclases().get("peso") != null) {
            this.peso = datos.atributosDeSubclases().get("peso");
        }
        if (datos.atributosDeSubclases().get("venta_unidad") != null) {
            this.ventaPorUnidad = Boolean.parseBoolean(datos.atributosDeSubclases().get("venta_unidad"));
        }
        if (datos.atributosDeSubclases().get("empaque") != null) {
            this.empaque = TipoEmpaque.valueOf(datos.atributosDeSubclases().get("empaque"));
        }
        if (datos.atributosDeSubclases().get("tipo_carne") != null) {
            this.tipo = TipoCarne.valueOf(datos.atributosDeSubclases().get("tipo_carne"));
        }
    }

}
