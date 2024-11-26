package com.store.cincomenos.domain.dto.persona.employee.jobPosition;

import com.store.cincomenos.domain.persona.employee.jobPosition.JobPosition;

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
