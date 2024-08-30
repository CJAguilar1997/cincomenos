package com.tienda.cincomenos.domain.producto.validadores.carne;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.tienda.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;
import com.tienda.cincomenos.infra.exception.producto.InvalidValueException;
import com.tienda.cincomenos.infra.exception.producto.NullKeyException;

@Component
public class ValidadorEmpaque implements ValidadorDeProductos{

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.CARNES;
    }

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (!datos.atributosDeSubclases().containsKey("empaque")) {
            throw new NullKeyException(HttpStatus.CONFLICT, "El atributo empaque es obligatorio. Revisa tus datos");
        }

        if (datos.atributosDeSubclases().get("empaque").isEmpty()) {
            throw new InvalidValueException(HttpStatus.CONFLICT, "El atributo empaque no puede estar vacio");
        }
    }

}
