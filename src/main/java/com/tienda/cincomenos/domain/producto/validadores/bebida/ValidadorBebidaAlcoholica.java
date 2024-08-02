package com.tienda.cincomenos.domain.producto.validadores.bebida;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.domain.producto.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.dto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;

@Component
public class ValidadorBebidaAlcoholica implements ValidadorDeProductos{

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.BEBIDAS;
    }
    
    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (!datos.atributosDeSubclases().containsKey("bebida_alcoholica")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El atributo bebida_alcoholica es obligatorio. Revisa tus datos");
        }

        String valor = datos.atributosDeSubclases().get("bebida_alcoholica");
        
        if (!Arrays.asList("true", "false").contains(valor)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El atributo bebida_alcoholica debe de ser true o false");
        }
    }
}
