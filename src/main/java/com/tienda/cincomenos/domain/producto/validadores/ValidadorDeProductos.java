package com.tienda.cincomenos.domain.producto.validadores;

import com.tienda.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;

public interface ValidadorDeProductos {
    boolean supports(CategoriaProducto categoria);
    void validar(DatosRegistrarProducto datos);
}

