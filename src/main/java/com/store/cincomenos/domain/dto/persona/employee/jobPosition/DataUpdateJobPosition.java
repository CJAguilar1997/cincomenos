package com.store.cincomenos.domain.dto.persona.employee.jobPosition;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DataUpdateJobPosition(
    @NotBlank
    @Positive(message = "Id only accept positive numbers")
    @Digits(integer = 15, fraction = 0, message = "The id contains an invalid number")
    Long id,

    @Pattern(regexp = "[\\p{L} ]{0,40}")
    String name
) {

}
