package com.store.cincomenos.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.store.cincomenos.domain.dto.persona.employee.DataListEmployee;
import com.store.cincomenos.domain.dto.persona.employee.DataRegisterEmployee;
import com.store.cincomenos.domain.dto.persona.employee.DataResponseEmployee;
import com.store.cincomenos.domain.dto.persona.employee.DataResponseEmployeeLogin;
import com.store.cincomenos.domain.dto.persona.employee.DataUpdateEmployee;
import com.store.cincomenos.domain.persona.employee.EmployeeService;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.console.LogicalDeleteOperationException;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/employee")
@PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Object> registerEmployee(@RequestBody @Valid DataRegisterEmployee data, UriComponentsBuilder uriComponentsBuilder) {
        try {
            DataResponseEmployeeLogin reply = employeeService.register(data);
            URI url = uriComponentsBuilder.path("/employee/{id}").buildAndExpand(reply.dataResponseEmployee().id()).toUri();
            return ResponseEntity.status(HttpStatus.CREATED).location(url).body(reply);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<Page<DataListEmployee>> getListOfEmployeeByParameters(
        @PageableDefault(size = 30) Pageable pagination,
        @RequestParam(value = "id", required = false) Long id,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "dni", required = false) String dni,
        @RequestParam(value = "phone_number", required = false) String phoneNumber,
        @RequestParam(value = "registration_date", required = false) String registrationDate
    ) {
        Page<DataListEmployee> reply = employeeService.getList(pagination, id, name, dni, phoneNumber, registrationDate);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }
        
    @PutMapping
    public ResponseEntity<Object> updateEmployee(@Valid @RequestBody DataUpdateEmployee data) {
        try {
            DataResponseEmployee reply = employeeService.update(data);
            return ResponseEntity.status(HttpStatus.OK).body(reply);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> logicalDeleteEmployee(@RequestParam(value = "id", required = true) Long id) {
        try {
            employeeService.logicalDelete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  
        } catch (LogicalDeleteOperationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
