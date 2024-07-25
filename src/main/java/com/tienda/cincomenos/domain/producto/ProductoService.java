package com.tienda.cincomenos.domain.producto;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.cincomenos.domain.producto.bebida.Bebida;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository repository;

    public DatosRespuestaProducto registrar(DatosRegistrarProducto datos) {
        Producto producto;
        System.out.println(datos.nombre());
        if (datos.categoria().equals(CategoriaProducto.BEBIDAS)) {
            producto = repository.save(new Bebida(datos));
            Map<String, String> atributosDeSubclase = Map.of(
                "volumen", datos.atributosDeSubclases().get("volumen"), 
                "embase", datos.atributosDeSubclases().get("embase"), 
                "bebida_alcoholica", datos.atributosDeSubclases().get("bebida_alcoholica"), 
                "fecha_vencimiento", datos.atributosDeSubclases().get("fecha_vencimiento"));
            return new DatosRespuestaProducto(
                producto.getId(),
                producto.getCodigoDeBarras(),
                producto.getNombre(), 
                producto.getDescripcion(), 
                producto.getMarca(), 
                producto.getPrecio(), 
                producto.getStock(), 
                producto.getCategoria(), 
                atributosDeSubclase);
        } else {
            throw new RuntimeException("La categoria de producto elegida no existe");
        }
    }

}
