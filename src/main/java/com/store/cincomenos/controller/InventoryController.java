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

import com.store.cincomenos.domain.dto.product.DataListProducts;
import com.store.cincomenos.domain.dto.product.DataRegisterProduct;
import com.store.cincomenos.domain.dto.product.DataResponseProduct;
import com.store.cincomenos.domain.dto.product.DataUpdateProduct;
import com.store.cincomenos.domain.dto.product.ProductFilterDTO;
import com.store.cincomenos.domain.product.InventoryService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> registerProduct(@Valid @RequestBody DataRegisterProduct data, UriComponentsBuilder uriComponentsBuilder) {
        DataResponseProduct reply = service.register(data);
        URI url = uriComponentsBuilder.path("/inventory/{id}").buildAndExpand(reply.id()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(url).body(reply);
        
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Object> updateProduct(@RequestBody @Valid DataUpdateProduct data) {
        DataResponseProduct reply = service.update(data);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }

    @GetMapping
    public ResponseEntity<Page<DataListProducts>> listProductsByParameters(
        @PageableDefault(size = 30, sort = "id") Pageable pagination,
        @RequestBody @Valid ProductFilterDTO filter) {
        Page<DataListProducts> reply = service.listByParameters(pagination, filter);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> deleteProduct(@RequestParam(value = "id", required = true) @Min(1) Long id) {
        service.logicalDelete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
