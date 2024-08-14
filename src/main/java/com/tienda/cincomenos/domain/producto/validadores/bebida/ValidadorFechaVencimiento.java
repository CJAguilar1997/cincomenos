package com.tienda.cincomenos.domain.producto.validadores.bebida;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.domain.dto.producto.DatosRegistrarProducto;
import com.tienda.cincomenos.domain.producto.productoBase.CategoriaProducto;
import com.tienda.cincomenos.domain.producto.validadores.ValidadorDeProductos;

@Component
public class ValidadorFechaVencimiento implements ValidadorDeProductos {

    @Override
    public boolean supports(CategoriaProducto categoria) {
        return categoria == CategoriaProducto.BEBIDAS;
    }

    @Override
    public void validar(DatosRegistrarProducto datos) {
        String fechaVencimiento = datos.atributosDeSubclases().get("fecha_vencimiento");
        if (fechaVencimiento == null || fechaVencimiento.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El atributo fecha_vencimiento es obligatorio. Revisa tus datos");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaVencimientoFormateada = LocalDate.parse(fechaVencimiento, formatter);

        if (fechaVencimientoFormateada.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La fecha de vencimiento no puede ser anterior a la fecha actual");
        }
    }
}
