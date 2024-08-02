package com.tienda.cincomenos.domain.producto;

import java.util.Map;

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
