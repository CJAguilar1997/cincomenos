package com.tienda.cincomenos.domain.producto.validadores.validadores_producto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;

@Component
public class ValidarLlaveValorMap implements ValidadorDeProductos{

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return true;
    }

    @Override
    public void validar(DatosRegistrarProducto datos) {
        String patternKey = "^[a-z_]{1,30}$";
        String patternValue = "^[\\p{L}0-9-. ]+$";

        Pattern regexPatternKey = Pattern.compile(patternKey);
        Pattern regexPatternValue = Pattern.compile(patternValue);

        datos.atributosDeSubclases().forEach((key, value) -> {
            System.out.println(String.format("%s, %s", key, value));
            Matcher matcherKey = regexPatternKey.matcher(key);
            Matcher matcherValue = regexPatternValue.matcher(value);
            if (!matcherKey.matches()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("El atributo %s no es valido", key));
            }
            if (!matcherValue.matches()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("El valor %s del atributo %s no es valido", value, key));
            }
        });
    }

}
