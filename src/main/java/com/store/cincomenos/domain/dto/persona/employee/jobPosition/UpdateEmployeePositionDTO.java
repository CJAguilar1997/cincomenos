package com.store.cincomenos.domain.dto.persona.employee.jobPosition;

import com.fasterxml.jackson.annotation.JsonAlias;

public record UpdateEmployeePositionDTO(
    String type,
    String update,

    @JsonAlias({"position_to_update", "position_updatable"})
    String namePositionToUpdate,

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
