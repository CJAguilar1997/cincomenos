package com.store.cincomenos.domain.dto.persona.employee;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;
import com.store.cincomenos.domain.persona.employee.Employee;
import com.store.cincomenos.domain.persona.employee.EmployeeDeptPosition;

@JsonPropertyOrder({"id", "name", "dni", "departament", "contact_information"})
public record DataListEmployee(
    Long id,
    String name,
    String dni,

    @JsonProperty("departament_position")
    List<EmployeeDeptPositionDTO> employeeDeptPositions,
    
    @JsonProperty("contact_information")
    ContactInformationDTO contact
) {

    public DataListEmployee(Employee employee) {
        this(employee.getId(), employee.getName(), employee.getDni(), getDeptPosition(employee.getEmployeeDeptPositions()), new ContactInformationDTO(employee.getContact()));
    }

    private static List<EmployeeDeptPositionDTO> getDeptPosition(List<EmployeeDeptPosition> employeeDeptPositions) {
        return employeeDeptPositions.stream()
            .map(edp -> new EmployeeDeptPositionDTO(edp))
            .distinct()
            .collect(Collectors.toList());
    }
}
