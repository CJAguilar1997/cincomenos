package com.store.cincomenos.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.store.cincomenos.domain.dto.departament.DataListDepartament;
import com.store.cincomenos.domain.dto.departament.DataRegisterDepartament;
import com.store.cincomenos.domain.dto.departament.DataResponseDepartament;
import com.store.cincomenos.domain.dto.departament.DepartamentFilterDTO;
import com.store.cincomenos.domain.persona.employee.departament.DepartamentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/departament")
public class DepartamentController {

    @Autowired
    private DepartamentService departamentService;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> registerDepartament(@RequestBody @Valid DataRegisterDepartament data, UriComponentsBuilder uriComponentsBuilder) {
        DataResponseDepartament reply = departamentService.register(data);
        URI url = uriComponentsBuilder.path("/departament/{id}").buildAndExpand(reply.id()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(url).body(reply);
    }

    @GetMapping
    public ResponseEntity<Page<DataListDepartament>> getListOfDepartament(
        @RequestBody @Valid DepartamentFilterDTO departamentFilterDTO,
        @PageableDefault(size = 20) Pageable pagination) {
        Page<DataListDepartament> reply = departamentService.getList(departamentFilterDTO, pagination);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }
}
