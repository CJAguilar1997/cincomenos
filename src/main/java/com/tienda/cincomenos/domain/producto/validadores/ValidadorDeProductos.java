package com.tienda.cincomenos.domain.producto.validadores;

import com.tienda.cincomenos.domain.producto.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.dto.DatosRegistrarProducto;

public interface ValidadorDeProductos {
    boolean supports(CategoriaProducto categoria);
    void validar(DatosRegistrarProducto datos);
}

