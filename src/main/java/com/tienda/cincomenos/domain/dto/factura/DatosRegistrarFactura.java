package com.tienda.cincomenos.domain.dto.factura;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DatosRegistrarFactura(
    List<Producto> items
) {
    public record Producto(
        @JsonAlias("codigo_barras")
        @Pattern(regexp = "[0-9]{0,25}", message = "El codigo de barras es invalido")
        String codigoDeBarras,

        
        @Pattern(regexp = "[\\p{L}0-9- ]+", message = "El nombre es invalido")
        String nombre,

        @Pattern(regexp = "[\\p{L}0-9- ]+", message = "La marca es invalida")
        String marca,

        @Positive(message = "El atributo cantidad solo puede contener valores positivos")
        @Digits(integer = 19, fraction = 0, message = "El atributo cantidad solo puede contener numeros enteros")
        Integer cantidad,
        
        @JsonAlias("precio_unitario")
        @Positive(message = "El atributo precio solo puede contener valores positivos")
        @Digits(integer = 10, fraction = 2, message = "El atributo precio solo puede contener como máximo 10 valores positivos y 2 decimales")
        BigDecimal precioUnitario
    ) {

    }
}
