package com.store.cincomenos.domain.dto.persona.empleado;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;
import com.store.cincomenos.domain.dto.persona.empleado.jobPosition.JobPositionDTO;
import com.store.cincomenos.domain.persona.empleado.Employee;

@JsonPropertyOrder({"id", "name", "dni", "job_position", "contact_information"})
public record DataListEmployee(
    Long id,
    String name,
    String dni,
    @JsonProperty("job_position")
    JobPositionDTO jobPosition,
    @JsonProperty("contact_information")
    ContactInformationDTO contact
) {

    public DataListEmployee(Employee employee) {
        this(employee.getId(), employee.getName(), employee.getDni(), new JobPositionDTO(employee.getJobPosition()), new ContactInformationDTO(employee.getContact()));
    }
}
