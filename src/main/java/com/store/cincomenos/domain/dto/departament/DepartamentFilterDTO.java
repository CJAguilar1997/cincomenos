package com.store.cincomenos.domain.dto.departament;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DepartamentFilterDTO(

    @Min(1)
    @Positive
    @Digits(integer = 12, fraction = 0, message = "The departament id cannot be a fraction number")
    Long id,

    @Pattern(regexp = "([\\p{L}]{3,25})?")
    String name
) {

    public String name() {
        return (name != null) ? name.toUpperCase() : null;
    }
}
