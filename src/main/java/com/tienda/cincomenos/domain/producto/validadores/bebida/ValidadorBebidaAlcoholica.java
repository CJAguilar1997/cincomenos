package com.tienda.cincomenos.domain.producto.validadores.bebida;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.tienda.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;
import com.tienda.cincomenos.infra.exception.producto.InvalidValueException;
import com.tienda.cincomenos.infra.exception.producto.NullKeyException;

@Component
public class ValidadorBebidaAlcoholica implements ValidadorDeProductos{

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.BEBIDAS;
    }
    
    @Override
    public void validar(DatosRegistrarProducto datos) {
        if (!datos.atributosDeSubclases().containsKey("bebida_alcoholica")) {
            throw new NullKeyException(HttpStatus.CONFLICT, "El atributo bebida_alcoholica es obligatorio. Revisa tus datos");
        }

        String valor = datos.atributosDeSubclases().get("bebida_alcoholica");
        
        if (!Arrays.asList("true", "false").contains(valor)) {
            throw new InvalidValueException(HttpStatus.CONFLICT, "El atributo bebida_alcoholica debe de ser true o false");
        }
    }
}
