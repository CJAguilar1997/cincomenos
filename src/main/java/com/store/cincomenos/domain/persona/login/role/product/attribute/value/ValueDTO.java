package com.store.cincomenos.domain.persona.login.role.product.attribute.value;

import com.store.cincomenos.domain.product.attribute.value.Value;

import jakarta.validation.constraints.Pattern;

public record ValueDTO(
    @Pattern(regexp = "[\\p{L}0-9 ]{3,50}", message = "The value attribute contains invalid characters")
    String value
) {

    public ValueDTO(Value value) {
        this(value.getValue());
    }

}
