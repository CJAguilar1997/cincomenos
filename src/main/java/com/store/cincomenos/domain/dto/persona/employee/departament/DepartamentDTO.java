package com.store.cincomenos.domain.dto.persona.employee.departament;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.PositionDTO;
import com.store.cincomenos.domain.persona.employee.departament.Departament;
import com.store.cincomenos.domain.persona.employee.departament.position.Position;

public record DepartamentDTO(
    @JsonProperty(index = 0)
    String name,

    @JsonProperty(value = "position", index = 1)
    List<PositionDTO> positionDTO
) {

    public DepartamentDTO(Departament departament) {
        this(departament.getName(), getPositions(departament.getPositions()));
    }

    public String name() {
        return name.toUpperCase();
    }

    private static List<PositionDTO> getPositions(List<Position> positions) {
        return positions.stream()
            .map(position -> new PositionDTO(position))
            .toList();
    }
}
