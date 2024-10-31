package com.store.cincomenos.domain.producto.validadores.carne;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.store.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.store.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.store.cincomenos.domain.producto.validadores.ValidadorDeProductos;
import com.store.cincomenos.infra.exception.producto.InvalidValueException;
import com.store.cincomenos.infra.exception.producto.NullKeyException;

@Component
public class ValidadorVentaPorUnidad implements ValidadorDeProductos{

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.CARNES;
    }

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (!datos.atributosDeSubclases().containsKey("venta_unidad")) {
            throw new NullKeyException(HttpStatus.CONFLICT, "El atributo venta_unidad es obligatorio. Revisa tus datos");
        }

        String valor = datos.atributosDeSubclases().get("venta_unidad");

        if (!Arrays.asList("true", "false").contains(valor)) {
            throw new InvalidValueException(HttpStatus.CONFLICT, "El atributo venta_unidad debe de ser true o false");
        }
    }

}
