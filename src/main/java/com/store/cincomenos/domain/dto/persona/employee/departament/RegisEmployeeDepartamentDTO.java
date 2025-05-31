package com.store.cincomenos.domain.dto.persona.employee.departament;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.RegisEmployeePositionDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisEmployeeDepartamentDTO(
    @NotBlank
    String name,

    @NotNull
    @JsonAlias({"position", "job_position"})
    RegisEmployeePositionDTO positionDTO
) {
    
    public String name() {
        return this.name.toUpperCase();
    }
}
