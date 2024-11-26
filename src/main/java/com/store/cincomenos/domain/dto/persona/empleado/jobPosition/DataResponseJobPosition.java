package com.store.cincomenos.domain.dto.persona.empleado.jobPosition;

import com.store.cincomenos.domain.persona.empleado.jobPosition.JobPosition;

public record DataResponseJobPosition(
    Long id,
    String name
) {

    public DataResponseJobPosition(JobPosition jobPosition) {
        this(jobPosition.getId(), jobPosition.getName());
    }

}
