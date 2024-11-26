package com.store.cincomenos.domain.dto.persona.empleado;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;
import com.store.cincomenos.domain.dto.persona.UpdateData;
import com.store.cincomenos.domain.dto.persona.empleado.jobPosition.JobPositionDTO;

import jakarta.validation.constraints.NotNull;

public record DataUpdateEmployee(
    @NotNull
    Long id,

    String name,
    String dni,

    @JsonAlias({"job_position", "position"})
    JobPositionDTO jobPosition,

    @JsonAlias({"contact_information", "contact"})
    ContactInformationDTO contactInformationDTO
    
) implements UpdateData{
    
}
