package com.store.cincomenos.domain.dto.product.attribute;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DataRegisterAttribute(
    @NotBlank
    @Pattern(regexp = "[\\p{L} ]{3,20}", message = "The name of attribute contains invalid characters")
    String name
) {

    public String name() {
        return name.toUpperCase();
    }
}
