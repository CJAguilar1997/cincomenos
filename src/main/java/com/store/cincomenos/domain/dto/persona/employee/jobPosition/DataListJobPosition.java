package com.store.cincomenos.domain.dto.persona.employee.jobPosition;

import com.store.cincomenos.domain.persona.employee.departament.position.Position;

public record DataListJobPosition(
    Long id,
    String name
) {

    public DataListJobPosition(Position jobPosition) {
        this(jobPosition.getId(), jobPosition.getName());
    }
}
