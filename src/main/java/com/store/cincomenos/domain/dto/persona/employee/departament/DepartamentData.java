package com.store.cincomenos.domain.dto.persona.employee.departament;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = RegisEmployeeDepartamentDTO.class, name = "regis"),
    @JsonSubTypes.Type(value = UpdateEmployeeDepartamentDTO.class, name = "update")
})
public interface DepartamentData {
    String type();
    String name();
    PositionData positionDTO();
}
