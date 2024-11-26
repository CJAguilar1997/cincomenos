package com.store.cincomenos.domain.dto.persona.employee.jobPosition;

import com.store.cincomenos.domain.persona.employee.jobPosition.JobPosition;

public record DataResponseJobPosition(
    Long id,
    String name
) {

    public DataResponseJobPosition(JobPosition jobPosition) {
        this(jobPosition.getId(), jobPosition.getName());
    }

}
