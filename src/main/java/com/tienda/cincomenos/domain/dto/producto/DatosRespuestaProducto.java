package com.tienda.cincomenos.domain.dto.producto;

import java.math.BigDecimal;
import java.util.Map;

import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.productoBase.Producto;

public record DatosRespuestaProducto (
    Long id,
    String codigoDeBarras,
    String nombre, 
    String descripcion, 
    String marca, 
    BigDecimal precio, 
    Long stock, 
    CategoriaProducto categoria,
    Map<String, String> atributosDeSubclases) {

    public DatosRespuestaProducto(Producto producto) {
        this(producto.getId(), producto.getCodigoDeBarras(), producto.getNombre(), producto.getDescripcion(), producto.getMarca(), producto.getPrecio(), producto.getStock(), producto.getCategoria(), producto.getAtributosSubclases());
    }

}
