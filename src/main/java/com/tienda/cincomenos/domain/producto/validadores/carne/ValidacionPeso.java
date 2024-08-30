package com.tienda.cincomenos.domain.producto.validadores.carne;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.tienda.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;
import com.tienda.cincomenos.infra.exception.producto.InvalidValueException;
import com.tienda.cincomenos.infra.exception.producto.NullKeyException;

@Component
public class ValidacionPeso implements ValidadorDeProductos{

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.CARNES;
    }

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (!datos.atributosDeSubclases().containsKey("peso")) {
            throw new NullKeyException(HttpStatus.CONFLICT, "El atributo peso es obligatorio. Revisa tus datos");
        }

        if (datos.atributosDeSubclases().get("peso").isEmpty()) {
            throw new InvalidValueException(HttpStatus.CONFLICT, "El atributo peso no puede estar vacio");
        }
    }
}
