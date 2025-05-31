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

import com.store.cincomenos.domain.dto.product.attribute.DataListAttributes;
import com.store.cincomenos.domain.dto.product.attribute.DataRegisterAttribute;
import com.store.cincomenos.domain.dto.product.attribute.DataResponseAttribute;
import com.store.cincomenos.domain.dto.product.attribute.DataUpdateAttribute;
import com.store.cincomenos.domain.product.attribute.AttributeService;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/attribute")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @Transactional
    @PostMapping
    public ResponseEntity<Object> registerAttribute(@Valid @RequestBody DataRegisterAttribute data, UriComponentsBuilder uriComponentsBuilder) {
        try {
            DataResponseAttribute reply = attributeService.register(data);
            URI url = uriComponentsBuilder.path("/attribute/{id}").buildAndExpand(reply.id()).toUri();
            return ResponseEntity.status(HttpStatus.CREATED).location(url).body(reply);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  
        }
    }
    
    @GetMapping
    public ResponseEntity<Page<DataListAttributes>> getListAttribute(
        @RequestParam(value = "id", required = false) Long id,
        @RequestParam(value = "name", required = false) String name,
        @PageableDefault(size = 20) Pageable pagination) {
            Page<DataListAttributes> reply = attributeService.getList(id, name, pagination);
            return ResponseEntity.status(HttpStatus.OK).body(reply);
        }
        
    @Transactional
    @PutMapping
    public ResponseEntity<Object> updateAttribute(@Valid @RequestBody DataUpdateAttribute data) {
        try {
            DataResponseAttribute reply = attributeService.update(data);
            return ResponseEntity.status(HttpStatus.OK).body(reply);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  
        }
    }
    
    @Transactional
    @DeleteMapping
    public ResponseEntity<Object> deleteAttribute(@RequestParam(value = "id", required = true) Long id) {
        try {
            attributeService.logicalDelete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  
        }
    }
}
