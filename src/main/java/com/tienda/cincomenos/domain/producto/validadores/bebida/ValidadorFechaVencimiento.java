package com.tienda.cincomenos.domain.producto.validadores.bebida;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.domain.producto.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;

@Component
public class ValidadorFechaVencimiento implements ValidadorDeProductos {

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.BEBIDAS;
    }

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (datos.atributosDeSubclases().get("fecha_vencimiento") == null || datos.atributosDeSubclases().get("fecha_vencimiento").isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El atributo fecha_vencimiento es obligatorio. Revisa tus datos");
        }
    }
}
