package com.tienda.cincomenos.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.tienda.cincomenos.domain.dto.producto.DatosActualizarProducto;
import com.tienda.cincomenos.domain.dto.producto.DatosListadoProducto;
import com.tienda.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.dto.producto.DatosRespuestaProducto;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.productoBase.InventarioService;
import com.tienda.cincomenos.domain.producto.productoBase.Producto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService service;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> RegistrarProducto(@Valid @RequestBody DatosRegistrarProducto datos, UriComponentsBuilder uriComponentsBuilder) {
        DatosRespuestaProducto respuesta = service.registrar(datos);
        URI url = uriComponentsBuilder.path("/inventario/{id}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(url).body(respuesta);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaProducto> actualizarProducto(@RequestBody @Valid DatosActualizarProducto datos) {
        Producto respuesta = service.actualizar(datos);
        return ResponseEntity.status(HttpStatus.OK).body(new DatosRespuestaProducto(respuesta));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoProducto>> listarProductosPorParametros(
        @PageableDefault(size = 30, sort = "id") Pageable paginacion,
        @RequestParam(value = "id", required = false) Long id,
        @RequestParam(value = "nombre", required = false) String nombre,
        @RequestParam(value = "marca", required = false) String marca,
        @RequestParam(value = "categoria", required = false) CategoriaProducto categoria,
        @RequestParam(value = "precioMin", required = false) BigDecimal precioMin,
        @RequestParam(value = "precioMax", required = false) BigDecimal precioMax,
        @RequestParam(value = "codigoBarras", required = false) String codigoBarras) {
        Page<DatosListadoProducto> respuesta = service.listarPorParametros(paginacion, id, nombre, marca, categoria, precioMin, precioMax, codigoBarras);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> borrarProducto(@PathVariable Long id) {
        service.borrar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
