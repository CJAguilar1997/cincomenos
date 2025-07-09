package com.store.cincomenos.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/employee")
@PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
@Tag(name = "EmployeeController", description = "This controller contains the CRUDs related to employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(
        summary = "Employee register",
        description = "This method is for registering a new employee",
        responses = {
            @ApiResponse(responseCode = "201", description = "When the user does introduce all the values with valid characters"),
            @ApiResponse(responseCode = "400", description = "When the user does introduce invalid values"),
            @ApiResponse(responseCode = "403", description = "When the user doesn't introduce a token")
        }
    )
    @PostMapping
    @Transactional
    public ResponseEntity<Object> registerEmployee(@RequestBody @Valid DataRegisterEmployee data, UriComponentsBuilder uriComponentsBuilder) {
        DataResponseEmployeeLogin reply = employeeService.register(data);
        URI url = uriComponentsBuilder.path("/employee/{id}").buildAndExpand(reply.dataResponseEmployee().id()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(url).body(reply);
    }
    
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Employee listing",
        required = false,
        content = @Content(
            mediaType = "application/json",
            examples = {
                @ExampleObject(
                    name = "EmptyListing",
                    summary = "Example to how linting without params",
                    value = "{}"
                )
            }
        )
    )
    @Operation(
        summary = "Listing employee",
        description = "This method is for listing employees",
        responses = {
            @ApiResponse(responseCode = "200", description = "When the user doesn't introduce values or does introduce valid params"),
            @ApiResponse(responseCode = "400", description = "When the user does introduce invalid params"),
            @ApiResponse(responseCode = "403", description = "When the user doesn't introduce a token")
        }
    )
    @GetMapping
    public ResponseEntity<Page<DataListEmployee>> getListOfEmployeeByParameters(
        @PageableDefault(size = 30) Pageable pagination,
        @RequestParam(value = "id", required = false) @Min(1) Long id,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "dni", required = false) String dni,
        @RequestParam(value = "phone_number", required = false) String phoneNumber,
        @RequestParam(value = "registration_date", required = false) String registrationDate
    ) {
        Page<DataListEmployee> reply = employeeService.getList(pagination, id, name, dni, phoneNumber, registrationDate);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }
    
    @Operation(
        summary = "Employee updating",
        description = "This method is for updating a employee",
        responses = {
            @ApiResponse(responseCode = "200", description = "When the user does introduce all or some the values with valid characters"),
            @ApiResponse(responseCode = "400", description = "When the user does introduce invalid values"),
            @ApiResponse(responseCode = "403", description = "When the user doesn't introduce a token")
        }
    )
    @PutMapping
    @Transactional
    public ResponseEntity<Object> updateEmployee(@Valid @RequestBody DataUpdateEmployee data) {
        DataResponseEmployee reply = employeeService.update(data);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }
    
    @Operation(
        summary = "Employee logical deleting",
        description = "This method is for do a logical delete a employee",
        responses = {
            @ApiResponse(responseCode = "201", description = "When the user does introduce the id param with valid characters and positive"),
            @ApiResponse(responseCode = "400", description = "When the user does introduce invalid values"),
            @ApiResponse(responseCode = "403", description = "When the user doesn't introduce a token")
        }
    )
    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> logicalDeleteEmployee(@RequestParam(value = "id", required = true) @Min(1) Long id) {
        employeeService.logicalDelete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
