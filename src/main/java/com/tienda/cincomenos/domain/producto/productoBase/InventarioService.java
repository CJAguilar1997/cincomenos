package com.tienda.cincomenos.domain.producto.productoBase;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.domain.dto.producto.DatosActualizarProducto;
import com.tienda.cincomenos.domain.dto.producto.DatosListadoProducto;
import com.tienda.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.dto.producto.DatosRespuestaProducto;
import com.tienda.cincomenos.domain.producto.bebida.Bebida;
import com.tienda.cincomenos.domain.producto.carne.Carne;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;

import jakarta.validation.Valid;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository repository;
    
    @Autowired
    private List<ValidadorDeProductos> validadores;
    
    public DatosRespuestaProducto registrar(DatosRegistrarProducto datos) {
        Producto producto;
        
        List<ValidadorDeProductos> validadoresParaCategoria = validadores.stream()
            .filter(v -> v.supports(datos.categoria()))
            .collect(Collectors.toList());

        validadoresParaCategoria.forEach(v -> v.validar(datos));
        
        switch (datos.categoria()) {
            case BEBIDAS -> {
                producto = repository.save(new Bebida(datos));
                break;
            }
            case CARNES -> {
                producto = repository.save(new Carne(datos));
                break;
            }
            default -> {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoria de producto elegida no existe");
            }
        }
        return new DatosRespuestaProducto(producto);
    }

    public ResponseEntity<Page<DatosListadoProducto>> listar(Pageable paginacion) {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findByProductoActivoTrue(paginacion).map(DatosListadoProducto::new));
    }

    public void borrar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id no existe en la base de datos");
        } 
        Producto producto = repository.getReferenceById(id);
        producto.desactivarProducto();
    }

    public Producto actualizar(@Valid DatosActualizarProducto datos) {
        Producto producto = repository.getReferenceById(datos.id());
        producto.actualizarAtributos(datos);
        return producto;
    }

    public Page<DatosListadoProducto> listarPorParametros(Pageable paginacion, Long id, String nombre,
            String marca, CategoriaProducto categoria, BigDecimal precioMin, BigDecimal precioMax, String codigoBarras) {
        Page<DatosListadoProducto> listadoDeProductos = repository.findByParameters(id, nombre, marca, categoria, precioMin, precioMax, codigoBarras, paginacion).map(DatosListadoProducto::new);
        return listadoDeProductos;
    }
}
