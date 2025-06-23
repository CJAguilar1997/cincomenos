package com.store.cincomenos.domain.dto.persona.employee;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;
import com.store.cincomenos.domain.dto.persona.UpdateData;
import com.store.cincomenos.domain.dto.persona.employee.departament.UpdateEmployeeDepartamentDTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DataUpdateEmployee implements UpdateData {

    @NotNull
    @Positive(message = "Id only accept positive numbers")
    @Digits(integer = 15, fraction = 0, message = "The id contains an invalid number")
    private Long id;

    @Pattern(regexp = "[\\p{L} ]*")
    private String name;

    @Pattern(regexp = "[0-9]+(\\-?[0-9]+)*", message = "El DNI contains invalid symbols")
    private String dni;

    @JsonAlias({"departaments"})
    private List<UpdateEmployeeDepartamentDTO> departamentDTO;

    @JsonAlias({"contact_information", "contact"})
    private ContactInformationDTO contactInformationDTO;

    public void setDepartamentDTO(List<UpdateEmployeeDepartamentDTO> updatedDepartaments) {
        this.departamentDTO = updatedDepartaments;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDni() {
        return this.dni;
    }

    public List<UpdateEmployeeDepartamentDTO> getDepartamentDTO() {
        return departamentDTO;
    }

    public ContactInformationDTO getContactInformationDTO() {
        return contactInformationDTO;
    }
}
