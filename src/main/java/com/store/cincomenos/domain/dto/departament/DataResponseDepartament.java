package com.store.cincomenos.domain.dto.departament;

import com.store.cincomenos.domain.persona.employee.departament.Departament;

public record DataResponseDepartament(
    Long id, 
    String name
) {

    public DataResponseDepartament(Departament departament) {
        this(departament.getId(), departament.getName());
    }

}
