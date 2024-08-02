package com.tienda.cincomenos.domain.producto.validadores.validadores_producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.domain.producto.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.ProductoRepository;
import com.tienda.cincomenos.domain.producto.dto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;

@Component
public class CodigoDeBarrasExistente implements ValidadorDeProductos{

    @Autowired
    private ProductoRepository repository;

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (repository.existsByCodigoDeBarras(datos.codigoDeBarras())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El codigo de barras del producto ya existe en la base de datos");
        };
    }

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return true;
    }
}
