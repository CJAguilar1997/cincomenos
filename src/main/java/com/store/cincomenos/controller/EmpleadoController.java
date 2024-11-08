package com.store.cincomenos.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.store.cincomenos.domain.dto.persona.empleado.DatosActualizarEmpleado;
import com.store.cincomenos.domain.dto.persona.empleado.DatosListadoEmpleado;
import com.store.cincomenos.domain.dto.persona.empleado.DatosRegistrarEmpleado;
import com.store.cincomenos.domain.dto.persona.empleado.DatosRespuestaEmpleado;
import com.store.cincomenos.domain.dto.persona.empleado.DatosRespuestaEmpleadoLogin;
import com.store.cincomenos.domain.persona.empleado.EmpleadoService;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.console.LogicalDeleteOperationException;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/empleados")
@PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @PostMapping
    public ResponseEntity<Object> registroEmpleado(@RequestBody @Valid DatosRegistrarEmpleado datos, UriComponentsBuilder uriComponentsBuilder) {
        try {
            DatosRespuestaEmpleadoLogin respuesta = service.registrar(datos);
            URI url = uriComponentsBuilder.path("/empleados/{id}").buildAndExpand(respuesta.datos().id()).toUri();
            return ResponseEntity.status(HttpStatus.CREATED).location(url).body(respuesta);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<Page<DatosListadoEmpleado>> listarEmpleadosPorParametros(
        @PageableDefault(size = 30) Pageable paginacion,
        @RequestParam(value = "id", required = false) Long id,
        @RequestParam(value = "nombre", required = false) String nombre,
        @RequestParam(value = "dni", required = false) String dni,
        @RequestParam(value = "telefono", required = false) String telefono,
        @RequestParam(value = "fecha_registro", required = false) String fechaDeRegistro
    ) {
        Page<DatosListadoEmpleado> respuesta = service.listar(paginacion, id, nombre, dni, telefono, fechaDeRegistro);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
        
    @PutMapping
    public ResponseEntity<Object> actualizarEmpleado(@Valid @RequestBody DatosActualizarEmpleado datos) {
        try {
            DatosRespuestaEmpleado respuesta = service.actualizar(datos);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> borrarEmpleado(@RequestParam(value = "id", required = true) Long id) {
        try {
            service.borrar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  
        } catch (LogicalDeleteOperationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
