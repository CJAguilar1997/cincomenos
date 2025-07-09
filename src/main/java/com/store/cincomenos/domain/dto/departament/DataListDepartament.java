package com.store.cincomenos.domain.dto.departament;

import com.store.cincomenos.domain.persona.employee.departament.Departament;

public record DataListDepartament(
    Long id,
    String name
) {

    public DataListDepartament(Departament departament) {
        this(departament.getId(), departament.getName());
    }
}
