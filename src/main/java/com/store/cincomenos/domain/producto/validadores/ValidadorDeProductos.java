package com.store.cincomenos.domain.producto.validadores;

import com.store.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.store.cincomenos.domain.producto.productoBase.CategoriaProducto;

public interface ValidadorDeProductos {
    boolean supports(CategoriaProducto categoria);
    void validar(DatosRegistrarProducto datos);
}

