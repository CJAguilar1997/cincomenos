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

import com.store.cincomenos.domain.dto.persona.cliente.DatosActualizarCliente;
import com.store.cincomenos.domain.dto.persona.cliente.DatosListadoCliente;
import com.store.cincomenos.domain.dto.persona.cliente.DatosRegistrarCliente;
import com.store.cincomenos.domain.dto.persona.cliente.DatosRespuestaCliente;
import com.store.cincomenos.domain.persona.cliente.ClienteService;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.console.LogicalDeleteOperationException;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Transactional
    @PostMapping
    public ResponseEntity<Object> registrarCliente(@RequestBody @Valid DatosRegistrarCliente datos, UriComponentsBuilder uriComponentsBuilder) {
        try {
            DatosRespuestaCliente respuesta = service.registrar(datos);
            URI url = uriComponentsBuilder.path("/clientes/{id}").buildAndExpand(respuesta.id()).toUri();
            return ResponseEntity.status(HttpStatus.CREATED).location(url).body(respuesta);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  
        }
    }
    
    @Transactional
    @PutMapping
    public ResponseEntity<Object> actualizarCliente(@Valid @RequestBody DatosActualizarCliente datos) {
        try {
            DatosRespuestaCliente respuesta = service.actualizar(datos);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  
        }
    }
    

    @GetMapping
    public ResponseEntity<Page<DatosListadoCliente>> listarClientes(
        @RequestParam(value = "id", required = true) Long id, 
        @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<DatosListadoCliente> respuesta = service.listar(id, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
        
    @Transactional
    @DeleteMapping
    public ResponseEntity<Object> borrarClienteLogico(@RequestParam(value = "id", required = true) Long id) {
        try {
            service.borradoLogico(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  
        } catch (LogicalDeleteOperationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
