package com.tienda.cincomenos.domain.producto.validadores.carne;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;

@Component
public class ValidadorEmpaque implements ValidadorDeProductos{

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.CARNES;
    }

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (!datos.atributosDeSubclases().containsKey("empaque") || datos.atributosDeSubclases().get("empaque").isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El atributo empaque es obligatorio. Revisa tus datos");
        }
    }

}
