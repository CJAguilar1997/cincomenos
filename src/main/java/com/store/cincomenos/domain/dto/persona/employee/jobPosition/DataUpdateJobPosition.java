package com.store.cincomenos.domain.dto.persona.employee.jobPosition;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record DataUpdateJobPosition(
    @NotBlank
    @Positive
    Long id,
    String name
) {

}
