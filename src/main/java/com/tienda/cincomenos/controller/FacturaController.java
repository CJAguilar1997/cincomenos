package com.tienda.cincomenos.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.cincomenos.domain.dto.factura.DatosListadoFactura;
import com.tienda.cincomenos.domain.dto.factura.DatosRegistrarFactura;
import com.tienda.cincomenos.domain.dto.factura.DatosRespuestaFactura;
import com.tienda.cincomenos.domain.dto.producto.DatosListadoProductos;
import com.tienda.cincomenos.domain.factura.FacturaService;

@RestController
@RequestMapping("/facturacion")
public class FacturaController {

    @Autowired
    private FacturaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaFactura> registrarFactura(@RequestBody DatosRegistrarFactura datos) {
        DatosRespuestaFactura respuesta = service.registrar(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping("/getProducto")
    public ResponseEntity<Page<DatosListadoProductos>> obtenerProducto(
        @RequestParam(value = "codigo_barras", required = true) String codigoDeBarras,
        Pageable paginacion) {
            Page<DatosListadoProductos> respuesta = service.obtenerProducto(codigoDeBarras, paginacion);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoFactura>> listarFacturaPorParametros(
        @PageableDefault(size = 30, sort = "id") Pageable paginacion,
        @RequestParam(value = "id", required = false) Long id
    ) {
        Page<DatosListadoFactura> respuesta = service.listarPorParametros(paginacion, id);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}
