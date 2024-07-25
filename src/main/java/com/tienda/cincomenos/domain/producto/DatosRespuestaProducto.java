package com.tienda.cincomenos.domain.producto;

import java.util.Map;

public record DatosRespuestaProducto (
    Long id,
    String codigoDeBarras,
    String nombre, 
    String descripcion, 
    String marca, 
    Double precio, 
    Long stock, 
    CategoriaProducto categoria,
    Map<String, String> atributosDeSubclases) {

    public DatosRespuestaProducto(Producto producto) {
        this(producto.getId(), producto.getCodigoDeBarras(), producto.getNombre(), producto.getDescripcion(), producto.getMarca(), producto.getPrecio(), producto.getStock(), producto.getCategoria(), producto.getAtributosSubclases());
    }

}
