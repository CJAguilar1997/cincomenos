package com.store.cincomenos.domain.producto.validadores.validadores_producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.store.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.store.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.store.cincomenos.domain.producto.productoBase.InventarioRepository;
import com.store.cincomenos.domain.producto.validadores.ValidadorDeProductos;
import com.store.cincomenos.infra.exception.producto.BarcodeNotExistsException;

@Component
public class CodigoDeBarrasExistente implements ValidadorDeProductos{

    @Autowired
    private InventarioRepository repository;

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (repository.existsByCodigoDeBarras(datos.codigoDeBarras())) {
            throw new BarcodeNotExistsException(HttpStatus.CONFLICT, "El codigo de barras del producto ya existe en la base de datos");
        };
    }

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return true;
    }
}
