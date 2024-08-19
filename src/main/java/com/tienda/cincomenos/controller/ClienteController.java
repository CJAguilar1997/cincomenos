package com.tienda.cincomenos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.cincomenos.domain.dto.cliente.DatosActualizarCliente;
import com.tienda.cincomenos.domain.dto.cliente.DatosListadoCliente;
import com.tienda.cincomenos.domain.dto.cliente.DatosRegistrarCliente;
import com.tienda.cincomenos.domain.dto.cliente.DatosRespuestaCliente;
import com.tienda.cincomenos.domain.persona.cliente.ClienteService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<DatosRespuestaCliente> registrarCliente(@RequestBody @Valid DatosRegistrarCliente datos) {
        DatosRespuestaCliente respuesta = service.registrar(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping
    public ResponseEntity<DatosRespuestaCliente> actualizarCliente(@RequestBody DatosActualizarCliente datos) {
        DatosRespuestaCliente respuesta = service.actualizar(datos);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Page<DatosListadoCliente>> listarClientes(@PathVariable Long id, @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<DatosListadoCliente> respuesta = service.listar(id, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> borrarClienteLogico(@PathVariable Long id) {
        service.borradoLogico(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
