package com.store.cincomenos.domain.dto.persona.empleado;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;
import com.store.cincomenos.domain.dto.persona.empleado.jobPosition.JobPositionDTO;
import com.store.cincomenos.domain.persona.RegistrationData;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DataRegisterEmployee(
    
    @NotBlank
    @Pattern(regexp = "^\\w+\\s+\\w+.*$", message = "El nombre debe de contener al menos un nombre y un apellido")
    String name,

    @NotBlank
    @Pattern(regexp = "[0-9]+(\\-?[0-9]+)*", message = "El DNI contiene simbolos invalidos")
    String dni,

    @NotNull
    @JsonAlias({"job_position", "position"})
    JobPositionDTO jobPosition,

    @NotNull
    Set<String> roles,
    
    @JsonAlias({"contact_information", "contact"})
    ContactInformationDTO contactInformationDTO

) implements RegistrationData {
    
    public Set<String> roles() {
        return roles.stream()
            .map(role -> role.toUpperCase())
            .collect(Collectors.toSet());
    }
}
