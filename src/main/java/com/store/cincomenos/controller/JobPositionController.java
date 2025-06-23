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

import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataListJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataRegisterJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataResponseJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataUpdateJobPosition;
import com.store.cincomenos.domain.persona.employee.departament.position.JobPositionService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

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
        @RequestParam(value = "id", required = false) @Min(1) Long id,
        @RequestParam(value = "name", required = false) String name,
        @PageableDefault(size = 20) Pageable pagination) {
        Page<DataListJobPosition> reply = jobPositionService.getList(id, name, pagination);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Object> updateJobPosition(@RequestBody DataUpdateJobPosition data) {
        DataResponseJobPosition reply = jobPositionService.update(data);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
        
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> deleteJobPosition(@RequestParam(value = "id", required = true) @Min(1) Long id) {
        jobPositionService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        
    }
    
}
