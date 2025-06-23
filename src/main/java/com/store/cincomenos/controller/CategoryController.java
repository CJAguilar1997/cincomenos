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

import com.store.cincomenos.domain.dto.product.category.DataListCategories;
import com.store.cincomenos.domain.dto.product.category.DataRegisterCategory;
import com.store.cincomenos.domain.dto.product.category.DataResponseCategory;
import com.store.cincomenos.domain.dto.product.category.DataUpdateCategory;
import com.store.cincomenos.domain.product.category.CategoryService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Transactional
    @PostMapping
    public ResponseEntity<Object> registerCategory(@Valid @RequestBody DataRegisterCategory data, UriComponentsBuilder uriComponentsBuilder) {
        DataResponseCategory reply = categoryService.register(data);
        URI url = uriComponentsBuilder.path("/category/{id}").buildAndExpand(reply.id()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(url).body(reply);
        
    }
    
    @GetMapping
    public ResponseEntity<Page<DataListCategories>> getListCategories(
        @RequestParam(value = "id", required = false) @Min(1) Long id,
        @RequestParam(value = "name", required = false) String name,
        @PageableDefault(size = 20) Pageable pagination) {
        Page<DataListCategories> reply = categoryService.getList(id, name, pagination);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }
    
    @Transactional
    @PutMapping
    public ResponseEntity<Object> updateCategory(@Valid @RequestBody DataUpdateCategory data) {
        DataResponseCategory reply = categoryService.update(data);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }
    
    @Transactional
    @DeleteMapping
    public ResponseEntity<Object> deleteCategory(@RequestParam(value = "id", required = true) @Min(1) Long id) {
        categoryService.logicalDelete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
