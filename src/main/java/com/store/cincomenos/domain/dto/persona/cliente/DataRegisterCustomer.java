package com.store.cincomenos.domain.dto.persona.cliente;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;
import com.store.cincomenos.domain.persona.RegistrationData;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DataRegisterCustomer(
    @NotBlank
    @Pattern(regexp = "^\\w+\\s+\\w+.*$", message = "Customer name contains invalid characters")
    String name,

    @NotBlank
    @Pattern(regexp = "[0-9]+(\\-?[0-9]+)*", message = "The DNI contains invalid characters")
    String dni,

    @JsonAlias({"contact_information", "contact"})
    ContactInformationDTO contactInformationDTO

) implements RegistrationData{

}
