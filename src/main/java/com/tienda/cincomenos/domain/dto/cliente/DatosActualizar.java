package com.tienda.cincomenos.domain.dto.cliente;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public interface DatosActualizar {
    default Map<String, Object> getAtributos() {
        Map<String, Object> atributos = new HashMap<>();
        
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                if (value != null) {
                    atributos.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                e.getMessage();
                e.printStackTrace();
            }
        }
        return atributos;
    }
}
