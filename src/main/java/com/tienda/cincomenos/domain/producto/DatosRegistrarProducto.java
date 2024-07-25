package com.tienda.cincomenos.domain.producto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistrarProducto (

    @JsonProperty("codigo_barras")
    String codigoDeBarras,

    @NotBlank
    String nombre, 

    String descripcion, 
    String marca, 

    @NotBlank
    Double precio, 

    @NotBlank
    Long stock, 

    @NotBlank
    @JsonProperty("categoria")
    CategoriaProducto categoria,

    @JsonProperty("atributos_subclase")
    Map<String, String> atributosDeSubclases) {

}
