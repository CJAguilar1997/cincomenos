package com.store.cincomenos.domain.dto.persona.employee;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;
import com.store.cincomenos.domain.dto.persona.employee.departament.RegisEmployeeDepartamentDTO;
import com.store.cincomenos.domain.persona.RegistrationData;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class DataRegisterEmployee implements RegistrationData {

    @NotBlank
    @JsonProperty("name")
    @Pattern(regexp = "^\\w+\\s+\\w+.*$", message = "El nombre debe de contener al menos un nombre y un apellido")
    private String name;
    
    @NotBlank
    @JsonProperty("dni")
    @Pattern(regexp = "[0-9]+(\\-?[0-9]+)*", message = "El DNI contiene simbolos invalidos")
    private String dni;

    @NotNull
    @JsonProperty("departaments")
    private List<RegisEmployeeDepartamentDTO> departamentDTO;
    
    @NotNull
    @JsonProperty("roles")
    private Set<String> roles;

    @JsonProperty("contact")
    private ContactInformationDTO contactInformationDTO;
    
    public DataRegisterEmployee() {

    }

    public DataRegisterEmployee(String name, String dni, Set<String> roles, ContactInformationDTO contactInformationDTO, List<RegisEmployeeDepartamentDTO> departamentDTO) {
        this.name = name;
        this.dni = dni;
        this.departamentDTO = departamentDTO;
        this.roles = roles;
        this.contactInformationDTO = contactInformationDTO;
    }
    
    public String name() {
        return name;
    }

    public String dni() {
        return dni;
    }

    public List<RegisEmployeeDepartamentDTO> departamentDTO() {
        return departamentDTO;
    }

    public ContactInformationDTO contactInformationDTO() {
        return contactInformationDTO;
    }

    public void setDepartamentDTO(List<RegisEmployeeDepartamentDTO> updatedDepartaments) {
        this.departamentDTO = updatedDepartaments;
    }
    
    public Set<String> roles() {
        return roles.stream()
        .map(role -> role.toUpperCase())
            .collect(Collectors.toSet());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((dni == null) ? 0 : dni.hashCode());
        result = prime * result + ((departamentDTO == null) ? 0 : departamentDTO.hashCode());
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
        result = prime * result + ((contactInformationDTO == null) ? 0 : contactInformationDTO.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DataRegisterEmployee other = (DataRegisterEmployee) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (dni == null) {
            if (other.dni != null)
                return false;
        } else if (!dni.equals(other.dni))
            return false;
        if (departamentDTO == null) {
            if (other.departamentDTO != null)
                return false;
        } else if (!departamentDTO.equals(other.departamentDTO))
            return false;
        if (roles == null) {
            if (other.roles != null)
                return false;
        } else if (!roles.equals(other.roles))
            return false;
        if (contactInformationDTO == null) {
            if (other.contactInformationDTO != null)
                return false;
        } else if (!contactInformationDTO.equals(other.contactInformationDTO))
            return false;
        return true;
    }

}
