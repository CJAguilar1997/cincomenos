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

import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataListJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataRegisterJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataResponseJobPosition;
import com.store.cincomenos.domain.dto.position.PositionFilterDTO;
import com.store.cincomenos.domain.persona.employee.departament.position.JobPositionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/position")
public class JobPositionController {

    @Autowired
    private JobPositionService jobPositionService;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> registerJobPosition(@RequestBody @Valid DataRegisterJobPosition data, UriComponentsBuilder uriComponentsBuilder) {
        DataResponseJobPosition reply = jobPositionService.register(data);
        URI url = uriComponentsBuilder.path("/position/{id}").buildAndExpand(reply.id()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(url).body(reply);
    }

    @GetMapping
    public ResponseEntity<Page<DataListJobPosition>> getListOfJobPosition(
        @RequestBody @Valid PositionFilterDTO positionFilterDTO,
        @PageableDefault(size = 20) Pageable pagination) {
        Page<DataListJobPosition> reply = jobPositionService.getList(positionFilterDTO, pagination);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }
    
}
