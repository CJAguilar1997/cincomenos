package com.store.cincomenos.domain.dto.product.category;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DataUpdateCategory(
    @NotNull
    @Positive(message = "The id number cannot be negative")
    @Digits(integer = 12, fraction = 0, message = "The id number cannot contains fraction numbers")
    Long id,

    @NotBlank
    @Pattern(regexp = "[\\p{L} ]{0,50}")
    String name
) {

    public String name() {
        return name.toUpperCase();
    }
}
