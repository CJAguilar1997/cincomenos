package com.tienda.cincomenos.domain.producto.validadores.validadores_producto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;

import com.tienda.cincomenos.domain.dto.producto.DatosActualizarProducto;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.productoBase.Producto;
import com.tienda.cincomenos.infra.exception.producto.InvalidKeyException;
import com.tienda.cincomenos.infra.exception.producto.InvalidValueException;

import jakarta.persistence.Column;

public class ValidadorDatosActualizarProducto {

    public static void validar(DatosActualizarProducto datos) {
        
        Map<String, String> atributos = datos.atributosDeSubclases();
        
        atributos.forEach((key, value) -> {
            List<String> columns = extractNameColumn(datos.categoria());
            if (!columns.contains(key)) {
                throw new InvalidKeyException(HttpStatus.BAD_REQUEST, String.format("La llave %s no pertenece a el producto %s", key, datos.categoria().toString()));
            }
            getMatcherValue(key, value);
        });
        
    }

    
    private static List<String> extractNameColumn(CategoriaProducto categoria) {
        Class<? extends Producto> clazz = categoria.getAssociatedClass();
        Field[] fields = clazz.getDeclaredFields();
        
        List<String> columns = new ArrayList<>(); 
        
        for(Field field : fields) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation == null) {
                columns.add(field.getName());
            } else if (columnAnnotation != null){
                columns.add(columnAnnotation.name());
            }
        }
        return columns;
    }
    
    private static void getMatcherValue(String key, String value) {
        String patternKey = "^[a-z](\\_?[a-z]){1,30}$";
        String patternValue = "^[\\p{L}0-9-. ]+$";
    
        Pattern regexPatternKey = Pattern.compile(patternKey);
        Pattern regexPatternValue = Pattern.compile(patternValue);

        Matcher matcherKey = regexPatternKey.matcher(key);
        Matcher matcherValue = regexPatternValue.matcher(value);
        if (!matcherKey.matches()) {
            throw new InvalidKeyException(HttpStatus.BAD_REQUEST, String.format("El atributo %s no es valido", key));
        }
        if (!matcherValue.matches()) {
            throw new InvalidValueException(HttpStatus.BAD_REQUEST, String.format("El valor %s del atributo %s no es valido", value, key));
        }
    }
}
