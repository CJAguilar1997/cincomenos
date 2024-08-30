package com.tienda.cincomenos.domain.producto.validadores.bebida;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.tienda.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;
import com.tienda.cincomenos.infra.exception.producto.InvalidValueException;
import com.tienda.cincomenos.infra.exception.producto.NullKeyException;

@Component
public class ValidadorEmbase implements ValidadorDeProductos {
    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.BEBIDAS;
    }

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (!datos.atributosDeSubclases().containsKey("embase")) {
            throw new NullKeyException(HttpStatus.CONFLICT, "El atributo embase es obligatorio, revisa tus datos");
        }

        if (datos.atributosDeSubclases().get("embase").isEmpty()) {
            throw new InvalidValueException(HttpStatus.CONFLICT, "El atributo embase no puede estar vacio");
        }
    }
}
