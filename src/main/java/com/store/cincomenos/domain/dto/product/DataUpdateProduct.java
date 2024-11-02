package com.store.cincomenos.domain.dto.product;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DataUpdateProduct(
    @NotNull
    Long id,

    @Pattern(regexp = "[0-9]{6,25}", message = "The barcode only can contains numbers")
    String barcode,
    
    @Pattern(regexp = "[\\p{L}0-9 ]+(\\-?[\\p{L}0-9]+)*", message = "The name product contains invalid characters")
    String name, 

    @Pattern(regexp = "[\\p{L}0-9., ]+", message = "The description of product contains invalid characters")
    String description, 

    @Pattern(regexp = "[\\p{L}0-9 ]+(\\-?[\\p{L}0-9]+)*", message = "The brand product contains invalid characters")
    String brand, 

    @Positive(message = "The price only can contains positive integer numbers")
    @Digits(integer = 10, fraction = 2, message = "El atributo precio solo puede contener como máximo 10 valores positivos y 2 decimales")
    BigDecimal price,

    @NotNull
    CategoryDTO category,
    
    Set<AttributeDTO> attributes){

    public String name() {
        return this.name.toUpperCase();
    }
}
