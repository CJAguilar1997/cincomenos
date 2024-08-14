package com.tienda.cincomenos.domain.dto.producto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DatosActualizarProducto(
    @NotNull
    Long id,
    
    @Pattern(regexp = "[\\p{L}0-9- ]+", message = "El nombre es invalido")
    String nombre, 

    @Pattern(regexp = "[\\p{L}0-9., ]+", message = "La descripcion contiene caracteres invalidos")
    String descripcion, 

    @Pattern(regexp = "[\\p{L}0-9- ]+", message = "La marca es invalida")
    String marca, 

    @Positive(message = "El atributo precio solo puede contener valores positivos")
    @Digits(integer = 10, fraction = 2, message = "El atributo precio solo puede contener como máximo 10 valores positivos y 2 decimales")
    BigDecimal precio,

    @NotNull
    CategoriaProducto categoria,
    
    @JsonAlias("atributos_subclases")
    Map<String, String> atributosDeSubclases) {

}
