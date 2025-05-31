package com.store.cincomenos.domain.dto.persona.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.cincomenos.domain.persona.employee.EmployeeDeptPosition;

public record EmployeeDeptPositionDTO(
    
    @JsonProperty("departament")
    ResponseDepartamentDTO departamentDto,

    @JsonProperty("position")
    ResponsePositionDTO positionDto
) {

    public EmployeeDeptPositionDTO(EmployeeDeptPosition deptPosition) {
        this(new ResponseDepartamentDTO(deptPosition.getDepartament()), new ResponsePositionDTO(deptPosition.getPosition()));
    }
}
