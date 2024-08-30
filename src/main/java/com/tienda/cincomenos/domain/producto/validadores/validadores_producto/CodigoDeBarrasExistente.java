package com.tienda.cincomenos.domain.producto.validadores.validadores_producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.tienda.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.productoBase.InventarioRepository;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;
import com.tienda.cincomenos.infra.exception.producto.BarcodeExistsException;

@Component
public class CodigoDeBarrasExistente implements ValidadorDeProductos{

    @Autowired
    private InventarioRepository repository;

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (repository.existsByCodigoDeBarras(datos.codigoDeBarras())) {
            throw new BarcodeExistsException(HttpStatus.CONFLICT, "El codigo de barras del producto ya existe en la base de datos");
        };
    }

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return true;
    }
}
