package com.tienda.cincomenos.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.tienda.cincomenos.domain.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.DatosRespuestaProducto;
import com.tienda.cincomenos.domain.producto.ProductoService;

@RestController
@RequestMapping("/inventario")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> RegistrarProducto(@RequestBody DatosRegistrarProducto datos, UriComponentsBuilder uriComponentsBuilder) {
        DatosRespuestaProducto respuesta = service.registrar(datos);
        URI url = uriComponentsBuilder.path("/inventario/{id}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(url).body(respuesta);
        
    }
}
