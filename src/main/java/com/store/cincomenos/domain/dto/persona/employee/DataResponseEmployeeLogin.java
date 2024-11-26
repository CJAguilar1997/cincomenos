package com.store.cincomenos.domain.dto.persona.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.cincomenos.domain.dto.persona.login.DataUserLoginResponse;
import com.store.cincomenos.domain.persona.employee.Employee;


public record DataResponseEmployeeLogin(
    @JsonProperty(value = "employee_data", index = 0)
    DataResponseEmployee dataResponseEmployee,
    @JsonProperty(value = "employee_login_data", index = 1)
    DataUserLoginResponse login
) {

    public DataResponseEmployeeLogin(Employee employee, DataUserLoginResponse loginData) {
        this(new DataResponseEmployee(employee), loginData);
    }

}
