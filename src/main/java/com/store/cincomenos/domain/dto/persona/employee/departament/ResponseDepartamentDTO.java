package com.store.cincomenos.domain.dto.persona.employee.departament;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.cincomenos.domain.persona.employee.departament.Departament;

public record ResponseDepartamentDTO(
    
    @JsonProperty("name")
    String departamentName

) {
    public ResponseDepartamentDTO(Departament departament) {
        this(departament.getName());
    }

}
