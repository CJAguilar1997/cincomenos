package com.store.cincomenos.domain.dto.producto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.store.cincomenos.domain.producto.productoBase.Producto;

public record DatosRespuestaProducto (
    Long id,
    @JsonProperty("codigo_barras")
    String codigoDeBarras,
    String nombre, 
    String descripcion, 
    String marca, 
    BigDecimal precio, 
    Long stock, 
    CategoriaProducto categoria,
    @JsonProperty("atributos_subclase")
    Map<String, String> atributosDeSubclases) {

    public DatosRespuestaProducto(Producto producto) {
        this(producto.getId(), producto.getCodigoDeBarras(), producto.getNombre(), producto.getDescripcion(), producto.getMarca(), producto.getPrecio(), producto.getStock(), producto.getCategoria(), producto.getAtributosSubclases());
    }

}
