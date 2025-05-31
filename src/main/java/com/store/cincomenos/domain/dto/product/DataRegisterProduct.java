package com.store.cincomenos.domain.dto.product;

import java.math.BigDecimal;
import java.util.List;

import com.store.cincomenos.domain.dto.product.attribute.AttributeDTO;
import com.store.cincomenos.domain.dto.product.category.CategoryDTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DataRegisterProduct(

    @NotBlank
    @Pattern(regexp = "[0-9]{0,25}", message = "The barcode is invalid")
    String barcode,

    @NotBlank
    @Pattern(regexp = "[\\p{L}0-9 ]+(\\-?[\\p{L}0-9]+)*", message = "The name product contains invalid characters")
    String name, 

    @Pattern(regexp = "[\\p{L}0-9., ]+", message = "The description of product contains invalid characters")
    String description, 

    @Pattern(regexp = "[\\p{L}0-9 ]+(\\-?[\\p{L}0-9]+)*", message = "The brand product contains invalid characters")
    String brand, 

    @NotNull
    @Positive(message = "The price only can contains positive integer numbers")
    @Digits(integer = 10, fraction = 2, message = "The price attribute can only contain a maximum of 10 positive digits and 2 decimal places")
    BigDecimal price, 
    
    @NotNull
    @Positive(message = "The stock only can contains positive integer numbers")
    @Digits(integer = 19, fraction = 0, message = "El attribute stock solo puede contener numeros enteros")
    Long stock, 

    @NotNull
    List<CategoryDTO> categories,

    List<AttributeDTO> attributes) {
    
    public List<CategoryDTO> getCategories() {
        return FilterUtils.getFiltredCategories(categories);
    }

    public List<AttributeDTO> attributes() {
        return FilterUtils.getFiltredAttribute(attributes);
    }
}
