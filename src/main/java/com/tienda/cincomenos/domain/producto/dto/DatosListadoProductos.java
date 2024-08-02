package com.tienda.cincomenos.domain.producto.dto;

import java.util.Map;

import com.tienda.cincomenos.domain.producto.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.Producto;

public record DatosListadoProductos(
    Long id,
    String codigoDeBarras,
    String nombre, 
    String descripcion, 
    String marca, 
    Double precio, 
    Long stock, 
    CategoriaProducto categoria,
    Map<String, String> atributosDeSubclases
) {

    public DatosListadoProductos(Producto producto) {
        this(producto.getId(), producto.getCodigoDeBarras(), producto.getNombre(), producto.getDescripcion(), producto.getMarca(), producto.getPrecio(), producto.getStock(), producto.getCategoria(), producto.getAtributosSubclases());
    }
}
