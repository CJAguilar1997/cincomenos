package com.store.cincomenos.domain.dto.persona.employee.departament;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.RegisEmployeePositionDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisEmployeeDepartamentDTO(
    @NotBlank
    @Pattern(regexp = "[\\p{L} ]{3, 40}", message = "The departament name only can contains letters and cannot contains minus of 3 or more then 40 characters")
    String name,

    @JsonAlias({"position", "job_position"})
    RegisEmployeePositionDTO positionDTO
) {
    
    public String name() {
        return this.name.toUpperCase();
    }
}
