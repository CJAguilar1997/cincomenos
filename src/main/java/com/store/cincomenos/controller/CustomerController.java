package com.store.cincomenos.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.store.cincomenos.domain.dto.persona.customer.DataListCustomers;
import com.store.cincomenos.domain.dto.persona.customer.DataRegisterCustomer;
import com.store.cincomenos.domain.dto.persona.customer.DataResponseCustomer;
import com.store.cincomenos.domain.dto.persona.customer.DataUpdateCustomer;
import com.store.cincomenos.domain.persona.customer.CustomerService;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.console.LogicalDeleteOperationException;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @Transactional
    @PostMapping
    public ResponseEntity<Object> registerCustomer(@RequestBody @Valid DataRegisterCustomer data, UriComponentsBuilder uriComponentsBuilder) {
        try {
            DataResponseCustomer reply = service.register(data);
            URI url = uriComponentsBuilder.path("/customer/{id}").buildAndExpand(reply.id()).toUri();
            return ResponseEntity.status(HttpStatus.CREATED).location(url).body(reply);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  
        }
    }
    
    @Transactional
    @PutMapping
    public ResponseEntity<Object> updateCustomer(@Valid @RequestBody DataUpdateCustomer data) {
        try {
            DataResponseCustomer reply = service.update(data);
            return ResponseEntity.status(HttpStatus.OK).body(reply);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  
        }
    }
    

    @GetMapping
    public ResponseEntity<Page<DataListCustomers>> getListOfCustomers(
        @RequestParam(value = "id", required = true) Long id, 
        @PageableDefault(size = 10, sort = "id") Pageable pagination) {
        Page<DataListCustomers> reply = service.getList(id, pagination);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }
        
    @Transactional
    @DeleteMapping
    public ResponseEntity<Object> logicalDeleteCustomer(@RequestParam(value = "id", required = true) Long id) {
        try {
            service.logicalDelete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  
        } catch (LogicalDeleteOperationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
