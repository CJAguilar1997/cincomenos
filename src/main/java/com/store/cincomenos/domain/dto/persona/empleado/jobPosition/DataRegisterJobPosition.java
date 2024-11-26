package com.store.cincomenos.domain.dto.persona.empleado.jobPosition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DataRegisterJobPosition(
    @NotNull
    @Pattern(regexp = "[\\p{L} ]{3,50}", message = "The job position name contains invalid characters")
    String name
) {

    public String name() {
        return name.toUpperCase();
    }
}
