package com.tienda.cincomenos.domain.producto.validadores.bebida;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;

@Component
public class ValidadorVolumen implements ValidadorDeProductos {
    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.BEBIDAS;
    }

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (!datos.atributosDeSubclases().containsKey("volumen") || datos.atributosDeSubclases().get("volumen").isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El atributo volumen es obligatorio. Revisa tus datos");
        }
    }
}

