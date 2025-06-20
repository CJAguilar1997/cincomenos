package com.store.cincomenos.domain.dto.persona.employee.jobPosition;

import jakarta.validation.constraints.NotBlank;

public record RegisEmployeePositionDTO(
    @NotBlank
    String name
) {

    public String name() {
        return this.name.toUpperCase();
    }
}
