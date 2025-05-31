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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.store.cincomenos.domain.dto.invoice.DatosListadoFactura;
import com.store.cincomenos.domain.dto.invoice.DatosRegistrarFactura;
import com.store.cincomenos.domain.dto.invoice.DatosRespuestaFactura;
import com.store.cincomenos.domain.dto.product.DataListProducts;
import com.store.cincomenos.domain.invoice.FacturaService;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.producto.OutOfStockException;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/facturacion")
public class FacturaController {

    @Autowired
    private FacturaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> registrarFactura(@Valid @RequestBody DatosRegistrarFactura datos, UriComponentsBuilder uriComponentsBuilder) {
        try {
            DatosRespuestaFactura respuesta = service.registrar(datos);
            URI url = uriComponentsBuilder.path("/facturacion/{id}").buildAndExpand(respuesta.id()).toUri();
            return ResponseEntity.status(HttpStatus.CREATED).location(url).body(respuesta);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  
        } catch (OutOfStockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());  
        }
    }

    @GetMapping("/getProducto")
    public ResponseEntity<Page<DataListProducts>> obtenerProducto(
        @RequestParam(value = "codigo_barras", required = true) String codigoDeBarras,
        Pageable paginacion) {
            Page<DataListProducts> respuesta = service.obtenerProducto(codigoDeBarras, paginacion);
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    } 

    @GetMapping
    public ResponseEntity<Page<DatosListadoFactura>> listarFacturaPorParametros(
        @PageableDefault(size = 30, sort = "id") Pageable paginacion,
        @RequestParam(value = "id", required = false) Long id,
        @RequestParam(value = "id_cliente", required = false) Long idCliente
    ) {
        Page<DatosListadoFactura> respuesta = service.listarPorParametros(paginacion, id, idCliente);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}
