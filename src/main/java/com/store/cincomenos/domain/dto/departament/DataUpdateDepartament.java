package com.store.cincomenos.domain.dto.departament;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DataUpdateDepartament(
    @Min(1)
    @Positive
    @Digits(integer = 12, fraction = 0, message = "The departament id cannot be a fraction number")
    Long id,

    @Pattern(regexp = "[\\p{L}]{3,25}", message = "The department name cannot have less than 3 characters and more than 25")
    String name
) {

}
