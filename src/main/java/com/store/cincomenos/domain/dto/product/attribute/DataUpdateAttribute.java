package com.store.cincomenos.domain.dto.product.attribute;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DataUpdateAttribute(
    @NotNull
    @Positive(message = "The id number can not be negative")
    Long id,

    @Pattern(regexp = "[\\p{L} ]{3,20}", message = "The name of attribute contains invalid characters")
    String name
) {

    public String name() {
        return name.toUpperCase();
    }
}
