package com.tienda.cincomenos.domain.producto;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizarProducto(
    @NotBlank
    Long id,
    String nombre, 
    String descripcion, 
    String marca, 
    Double precio,
    @NotBlank
    CategoriaProducto categoria,
    Map<String, String> atributosDeSubclases) {

}
