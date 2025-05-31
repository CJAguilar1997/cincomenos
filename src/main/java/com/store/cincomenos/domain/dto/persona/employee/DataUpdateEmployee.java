package com.store.cincomenos.domain.dto.persona.employee;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;
import com.store.cincomenos.domain.dto.persona.UpdateData;
import com.store.cincomenos.domain.dto.persona.employee.departament.UpdateEmployeeDepartamentDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DataUpdateEmployee implements UpdateData {

    @NotNull
    private Long id;

    private String name;
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

    public String dni() {
        return this.dni;
    }

    public List<UpdateEmployeeDepartamentDTO> getDepartamentDTO() {
        return departamentDTO;
    }

    public ContactInformationDTO contactInformationDTO() {
        return contactInformationDTO;
    }
}
