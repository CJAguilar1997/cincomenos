package com.tienda.cincomenos.domain.producto.productoBase;

import com.tienda.cincomenos.domain.producto.bebida.Bebida;
import com.tienda.cincomenos.domain.producto.carne.Carne;

public enum CategoriaProducto {
    BEBIDAS(Bebida.class),
    CARNES(Carne.class);

    private final Class<? extends Producto> associatedClass;

    CategoriaProducto(Class<? extends Producto> associatedClass) {
        this.associatedClass = associatedClass;
    }

    public Class<? extends Producto> getAssociatedClass() {
        return associatedClass;
    }
}
