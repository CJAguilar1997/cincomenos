package com.tienda.cincomenos.domain.producto.productoBase;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tienda.cincomenos.domain.dto.producto.DatosActualizarProducto;
import com.tienda.cincomenos.domain.dto.producto.DatosListadoProducto;
import com.tienda.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.dto.producto.DatosRespuestaProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;
import com.tienda.cincomenos.domain.producto.validadores.validadores_producto.ValidadorDatosActualizarProducto;
import com.tienda.cincomenos.infra.exception.EntityNotFoundException;

import jakarta.validation.Valid;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository repository;
    
    @Autowired
    private List<ValidadorDeProductos> validadores;
    
    public DatosRespuestaProducto registrar(@Valid DatosRegistrarProducto datos) {
        Producto producto = null;
        
        List<ValidadorDeProductos> validadoresParaCategoria = validadores.stream()
            .filter(v -> v.supports(datos.categoria()))
            .collect(Collectors.toList());

        validadoresParaCategoria.forEach(v -> v.validar(datos));

        Class<? extends Producto> productoClass = datos.categoria().getAssociatedClass();

        try {
            producto = repository.save(productoClass.getConstructor(DatosRegistrarProducto.class).newInstance(datos));

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | 
            IllegalArgumentException | InvocationTargetException | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("No se encontró un constructor asociado a la categoria: " + datos.categoria().toString());
        }
        return new DatosRespuestaProducto(producto);
    }

    public void borrar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(HttpStatus.BAD_REQUEST, "El id no existe en la base de datos");
        } 
        Producto producto = repository.getReferenceById(id);
        producto.desactivarProducto();
    }

    public Producto actualizar(@Valid DatosActualizarProducto datos) {
        if (!repository.existsById(datos.id())) {
            throw new EntityNotFoundException(HttpStatus.CONFLICT, "El id no existe en la base de datos");
        }
        ValidadorDatosActualizarProducto.validar(datos);
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
