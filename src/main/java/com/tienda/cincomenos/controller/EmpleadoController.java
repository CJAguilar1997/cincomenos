package com.tienda.cincomenos.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.tienda.cincomenos.domain.dto.persona.empleado.DatosActualizarEmpleado;
import com.tienda.cincomenos.domain.dto.persona.empleado.DatosListadoEmpleado;
import com.tienda.cincomenos.domain.dto.persona.empleado.DatosRegistrarEmpleado;
import com.tienda.cincomenos.domain.dto.persona.empleado.DatosRespuestaEmpleado;
import com.tienda.cincomenos.domain.dto.persona.empleado.DatosRespuestaEmpleadoLogin;
import com.tienda.cincomenos.domain.persona.empleado.EmpleadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @PostMapping
    public ResponseEntity<DatosRespuestaEmpleadoLogin> registroEmpleado(@RequestBody @Valid DatosRegistrarEmpleado datos, UriComponentsBuilder uriComponentsBuilder) {
        DatosRespuestaEmpleadoLogin respuesta = service.registrar(datos);
        URI url = uriComponentsBuilder.path("/empleados/{id}").buildAndExpand(respuesta.datos().id()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(url).body(respuesta);
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
    public ResponseEntity<DatosRespuestaEmpleado> actualizarEmpleado(@Valid @RequestBody DatosActualizarEmpleado datos) {
        DatosRespuestaEmpleado respuesta = service.actualizar(datos);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> borrarEmpleado(@PathVariable Long id) {
        service.borrar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
