package com.tienda.cincomenos.domain.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.cincomenos.domain.producto.bebida.Bebida;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository repository;

    public DatosRespuestaProducto registrar(DatosRegistrarProducto datos) {
        Producto producto;
        return switch (datos.categoria()) {
            case BEBIDAS -> {
                producto = repository.save(new Bebida(datos));
                yield new DatosRespuestaProducto(producto);
            } 
        default -> {
            throw new RuntimeException("La categoria de producto elegida no existe");
            }
        };
    }

}
