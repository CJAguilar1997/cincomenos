package com.tienda.cincomenos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.cincomenos.domain.dto.factura.DatosRegistrarFactura;
import com.tienda.cincomenos.domain.dto.factura.DatosRespuestaFactura;
import com.tienda.cincomenos.domain.factura.FacturaService;

@RestController
@RequestMapping("/facturacion")
public class FacturaController {

    @Autowired
    FacturaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaFactura> registrarFactura(@RequestBody DatosRegistrarFactura datos) {
        DatosRespuestaFactura respuesta = service.registrar(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}
