package com.tienda.cincomenos.domain.producto;

import com.tienda.cincomenos.domain.producto.bebida.Bebida;
import com.tienda.cincomenos.domain.producto.carne.Carne;

public enum CategoriaProducto {
    BEBIDAS(Bebida.class),
    CARNES(Carne.class);

    private final Class<?> clazz;

    CategoriaProducto(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
