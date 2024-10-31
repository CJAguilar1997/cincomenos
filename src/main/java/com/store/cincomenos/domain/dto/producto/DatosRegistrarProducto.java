package com.store.cincomenos.domain.dto.producto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.store.cincomenos.domain.producto.productoBase.CategoriaProducto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DatosRegistrarProducto(

    @NotBlank
    @JsonAlias("codigo_barras")
    @Pattern(regexp = "[0-9]{0,25}", message = "El codigo de barras es invalido")
    String codigoDeBarras,

    @NotBlank
    @Pattern(regexp = "[\\p{L}0-9 ]+(\\-?[\\p{L}0-9]+)*", message = "El nombre es invalido")
    String nombre, 

    @Pattern(regexp = "[\\p{L}0-9., ]+", message = "La descripcion contiene caracteres invalidos")
    String descripcion, 

    @Pattern(regexp = "[\\p{L}0-9 ]+(\\-?[\\p{L}0-9]+)*", message = "La marca es invalida")
    String marca, 

    @NotNull
    @Positive(message = "El atributo precio solo puede contener valores positivos")
    @Digits(integer = 10, fraction = 2, message = "El atributo precio solo puede contener como máximo 10 valores positivos y 2 decimales")
    BigDecimal precio, 
    
    @NotNull
    @Positive(message = "El atributo stock solo puede contener valores positivos")
    @Digits(integer = 19, fraction = 0, message = "El atributo stock solo puede contener numeros enteros")
    Long stock, 

    @NotNull
    CategoriaProducto categoria,

    @JsonAlias("atributos_subclase")
    Map<String, String> atributosDeSubclases){

}
