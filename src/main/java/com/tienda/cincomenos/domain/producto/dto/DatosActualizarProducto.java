package com.tienda.cincomenos.domain.producto.dto;

import java.util.Map;

import com.tienda.cincomenos.domain.producto.CategoriaProducto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarProducto(
    @NotNull
    Long id,
    String nombre, 
    String descripcion, 
    String marca, 
    Double precio,
    @NotNull
    CategoriaProducto categoria,
    Map<String, String> atributosDeSubclases) {

}
