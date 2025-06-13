package com.store.cincomenos.domain.dto.persona.employee.departament;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.UpdateEmployeePositionDTO;

import jakarta.validation.constraints.Pattern;

public record UpdateEmployeeDepartamentDTO(
    @Pattern(regexp = "[\\p{L}]{0,8}")
    String type,

    @Pattern(regexp = "[\\p{L}]{0,8}")
    String update,
    
    @Pattern(regexp = "[\\p{L} ]{0,20}")
    @JsonAlias({"departament_to_update", "departament_updatable"})
    String nameDeptToUpdate,
    
    @Pattern(regexp = "[\\p{L} ]{0,20}")
    @JsonAlias({"new_departament"})
    String nameDept,

    @JsonAlias({"positions", "job_positions"})
    UpdateEmployeePositionDTO positionDTO
) {
    public UpdateEmployeeDepartamentDTO(String type, String update, String nameDeptToUpdate, String nameDept, UpdateEmployeePositionDTO positionDTO) {
        this.type = (type == null) ? "update" : type;
        this.update = (update != null) ? update : "false";
        this.nameDeptToUpdate = (nameDeptToUpdate != null) ? nameDeptToUpdate.toUpperCase() : null;
        this.nameDept = (nameDept != null) ? nameDept.toUpperCase() : null;
        this.positionDTO = positionDTO;
    }
}
