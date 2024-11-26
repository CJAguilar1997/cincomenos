package com.store.cincomenos.domain.dto.persona.empleado.jobPosition;

import com.store.cincomenos.domain.persona.empleado.jobPosition.JobPosition;

public record JobPositionDTO(
    String name
) {

    public JobPositionDTO(JobPosition jobPosition) {
        this(jobPosition.getName());
    }

    public String name() {
        return this.name.toUpperCase();
    }
}
