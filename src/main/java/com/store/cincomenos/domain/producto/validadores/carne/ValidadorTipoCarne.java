package com.store.cincomenos.domain.producto.validadores.carne;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.store.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.store.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.store.cincomenos.domain.producto.validadores.ValidadorDeProductos;
import com.store.cincomenos.infra.exception.producto.InvalidValueException;
import com.store.cincomenos.infra.exception.producto.NullKeyException;

@Component
public class ValidadorTipoCarne implements ValidadorDeProductos{

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.CARNES;
    }

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (!datos.atributosDeSubclases().containsKey("tipo_carne")){
            throw new NullKeyException(HttpStatus.CONFLICT, "El atributo tipo_carne es obligatorio. Revisa tus datos");
        }

        if (datos.atributosDeSubclases().get("tipo_carne").isEmpty()) {
            throw new InvalidValueException(HttpStatus.CONFLICT, "El atributo empaque no puede estar vacio");
        }
    }

}