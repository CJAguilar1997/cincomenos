package com.tienda.cincomenos.domain.producto.validadores.carne;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.domain.producto.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;

@Component
public class ValidadorVentaPorUnidad implements ValidadorDeProductos{

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.CARNES;
    }

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (!datos.atributosDeSubclases().containsKey("venta_unidad")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El atributo venta_unidad es obligatorio. Revisa tus datos");
        }

        String valor = datos.atributosDeSubclases().get("venta_unidad");

        if (!Arrays.asList("true", "false").contains(valor)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El atributo venta_unidad debe de ser true o false");
        }
    }

}
