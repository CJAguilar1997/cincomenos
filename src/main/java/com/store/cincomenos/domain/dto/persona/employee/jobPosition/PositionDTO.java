package com.store.cincomenos.domain.dto.persona.employee.jobPosition;

import com.store.cincomenos.domain.persona.employee.departament.position.Position;

public record PositionDTO(
    String name
) {

    public PositionDTO(Position position) {
        this(position.getName());
    }

    public String name() {
        return this.name.toUpperCase();
    }
}
