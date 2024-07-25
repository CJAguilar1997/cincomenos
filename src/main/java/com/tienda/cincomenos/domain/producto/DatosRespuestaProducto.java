package com.tienda.cincomenos.domain.producto;

import java.util.Map;

public record DatosRespuestaProducto (
    Long id,
    String codigoDeBarras,
    String nombre, 
    String descripcion, 
    String marca, 
    Double precio, 
    Long cantidad, 
    CategoriaProducto categoria,
    Map<String, String> atributosDeSubclases) {

}
