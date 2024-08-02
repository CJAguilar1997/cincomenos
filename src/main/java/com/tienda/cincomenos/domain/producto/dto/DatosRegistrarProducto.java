package com.tienda.cincomenos.domain.producto.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tienda.cincomenos.domain.producto.CategoriaProducto;

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
