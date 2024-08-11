package com.tienda.cincomenos.domain.dto.producto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarProducto(
    @NotNull
    Long id,
    String nombre, 
    String descripcion, 
    String marca, 
    BigDecimal precio,
    @NotNull
    CategoriaProducto categoria,
    @JsonAlias("atributos_subclases")
    Map<String, String> atributosDeSubclases) {

}