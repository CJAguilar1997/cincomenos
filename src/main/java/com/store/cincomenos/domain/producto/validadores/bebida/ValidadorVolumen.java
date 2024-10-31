package com.store.cincomenos.domain.producto.validadores.bebida;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.store.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.store.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.store.cincomenos.domain.producto.validadores.ValidadorDeProductos;
import com.store.cincomenos.infra.exception.producto.InvalidValueException;
import com.store.cincomenos.infra.exception.producto.NullKeyException;

@Component
public class ValidadorVolumen implements ValidadorDeProductos {
    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.BEBIDAS;
    }

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (!datos.atributosDeSubclases().containsKey("volumen")) {
            throw new NullKeyException(HttpStatus.CONFLICT, "El atributo volumen es obligatorio. Revisa tus datos");
        }

        if (datos.atributosDeSubclases().get("volumen").isEmpty()) {
            throw new InvalidValueException(HttpStatus.CONFLICT, "El atributo volumen no puede estar vacio");
        }
    }
}

