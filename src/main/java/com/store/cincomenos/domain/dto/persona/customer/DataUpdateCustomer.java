package com.store.cincomenos.domain.dto.persona.customer;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;
import com.store.cincomenos.domain.dto.persona.UpdateData;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DataUpdateCustomer(
    @NotNull
    @Positive
    @Min(1)
    @Max(10)
    Long id,

    @Pattern(regexp = "[\\p{L} ]+", message = "El nombre contiene caracteres invalidos")
    String name,

    @Pattern(regexp = "[0-9]+(\\-?[0-9]+)*", message = "El DNI contiene simbolos invalidos")
    String dni,
    
    @JsonAlias("datos_contacto")
    ContactInformationDTO contactInformationDTO

) implements UpdateData {

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
