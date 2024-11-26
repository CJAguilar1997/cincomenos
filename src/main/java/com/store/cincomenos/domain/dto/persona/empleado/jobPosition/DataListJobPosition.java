package com.store.cincomenos.domain.dto.persona.empleado.jobPosition;

import com.store.cincomenos.domain.persona.empleado.jobPosition.JobPosition;

public record DataListJobPosition(
    Long id,
    String name
) {

    public DataListJobPosition(JobPosition jobPosition) {
        this(jobPosition.getId(), jobPosition.getName());
    }
}
