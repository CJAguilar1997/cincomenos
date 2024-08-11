package com.tienda.cincomenos.domain.dto.producto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistrarProducto (

    @JsonAlias("codigo_barras")
    String codigoDeBarras,

    @NotBlank
    String nombre, 

    String descripcion, 
    String marca, 

    @NotNull
    BigDecimal precio, 

    @NotNull
    Long stock, 

    @NotNull
    CategoriaProducto categoria,

    @JsonAlias("atributos_subclase")
    Map<String, String> atributosDeSubclases) {

}
