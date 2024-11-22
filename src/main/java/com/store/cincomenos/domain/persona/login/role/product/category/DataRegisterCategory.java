package com.store.cincomenos.domain.persona.login.role.product.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DataRegisterCategory(
    @NotBlank
    @Pattern(regexp = "[\\p{L} ]{3,20}", message = "The name of attribute contains invalid characters")
    String name
) {

    public String name() {
        return name.toUpperCase();
    }
}
