package com.tienda.cincomenos.domain.producto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DatosRegistrarProducto (
    @JsonProperty("codigo_barras")
    String codigoDeBarras,
    String nombre, 
    String descripcion, 
    String marca, 
    Double precio, 
    Long stock, 
    @JsonProperty("categoria")
    CategoriaProducto categoria,
    @JsonProperty("atributosDeSubclases")
    Map<String, String> atributosDeSubclases) {

}
