package com.tienda.cincomenos.domain.producto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistrarProducto (

    @JsonProperty("codigo_barras")
    String codigoDeBarras,

    @NotBlank
    String nombre, 

    String descripcion, 
    String marca, 

    @NotNull
    Double precio, 

    @NotNull
    Long stock, 

    @NotNull
    CategoriaProducto categoria,

    @JsonProperty("atributos_subclase")
    Map<String, String> atributosDeSubclases) {

}
