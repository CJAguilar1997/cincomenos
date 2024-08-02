package com.tienda.cincomenos.domain.producto.validadores.carne;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.domain.producto.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.dto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;

@Component
public class ValidadorTipoCarne implements ValidadorDeProductos{

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.CARNES;
    }

    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (!datos.atributosDeSubclases().containsKey("tipo_carne") || datos.atributosDeSubclases().get("tipo_carne").isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El atributo tipo_carne es obligatorio. Revisa tus datos");
        }
    }

}
