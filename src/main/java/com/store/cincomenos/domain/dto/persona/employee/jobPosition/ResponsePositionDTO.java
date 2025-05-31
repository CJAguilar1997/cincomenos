package com.store.cincomenos.domain.dto.persona.employee.jobPosition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.cincomenos.domain.persona.employee.departament.position.Position;

public record ResponsePositionDTO(

    @JsonProperty("name")
    String namePosition

) {

    public ResponsePositionDTO(Position position) {
        this(position.getName());
    }

}
