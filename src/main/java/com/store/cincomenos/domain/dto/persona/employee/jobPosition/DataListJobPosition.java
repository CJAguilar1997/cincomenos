package com.store.cincomenos.domain.dto.persona.employee.jobPosition;

import com.store.cincomenos.domain.persona.employee.jobPosition.JobPosition;

public record DataListJobPosition(
    Long id,
    String name
) {

    public DataListJobPosition(JobPosition jobPosition) {
        this(jobPosition.getId(), jobPosition.getName());
    }
}
