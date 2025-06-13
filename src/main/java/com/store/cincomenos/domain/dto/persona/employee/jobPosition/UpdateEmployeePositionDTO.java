package com.store.cincomenos.domain.dto.persona.employee.jobPosition;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Pattern;

public record UpdateEmployeePositionDTO(
    @Pattern(regexp = "[\\p{L}]{0,8}")
    String type,
    
    @Pattern(regexp = "[\\p{L}]{0,8}")
    String update,
    
    @Pattern(regexp = "[\\p{L} ]{0,20}")
    @JsonAlias({"position_to_update", "position_updatable"})
    String namePositionToUpdate,
    
    @Pattern(regexp = "[\\p{L} ]{0,20}")
    @JsonAlias({"new_position"})
    String namePosition
) {

    public UpdateEmployeePositionDTO(String type, String update, String namePositionToUpdate, String namePosition) {
        this.type = (type == null) ? "update" : type;
        this.update = (update != null) ? update : "false";
        this.namePositionToUpdate = (update == "true") ? namePositionToUpdate.toUpperCase() : null;
        this.namePosition = (namePosition != null) ? namePosition.toUpperCase() : null;
    }
}
