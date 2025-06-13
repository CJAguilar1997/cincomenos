package com.store.cincomenos.controller;

import java.math.BigDecimal;
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
import com.store.cincomenos.domain.product.InventoryService;

import jakarta.validation.Valid;

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
        @RequestParam(value = "id", required = false) Long id,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "brand", required = false) String brand,
        @RequestParam(value = "category", required = false) String category,
        @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
        @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
        @RequestParam(value = "barcode", required = false) String barcode) {
        Page<DataListProducts> reply = service.listByParameters(pagination, id, name, brand, category, minPrice, maxPrice, barcode);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> deleteProduct(@RequestParam(value = "id", required = true) Long id) {
        service.logicalDelete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
