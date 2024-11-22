package com.store.cincomenos.domain.persona.login.role.product.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DataUpdateCategory(
    @NotNull
    @Positive(message = "The id number can not be negative")
    Long id,

    @NotBlank
    String name
) {

    public String name() {
        return name.toUpperCase();
    }
}
