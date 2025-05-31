package com.store.cincomenos.domain.dto.invoice;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DataRegisterInvoice(
    
    @NotNull
    @JsonAlias("id_client")
    @Min(value = 1, message = "El id debe contener al menos un número")
    @Max(value = 10, message = "El id no puede superará los 10 digitos")
    @Positive
    Long idClient,

    List<Product> items
) {
    public record Product(
        @NotBlank
        @JsonAlias("barcode")
        @Pattern(regexp = "[0-9]{6,25}", message = "El codigo de barras debe contener al menos 6 digitos y como máximo 25")
        String barcode,

        @NotBlank
        @Pattern(regexp = "[\\p{L}0-9 ]+(\\-?[\\p{L}0-9 ]+)*", message = "El nombre es invalido")
        String name,

        @Pattern(regexp = "[\\p{L}0-9 ]+(\\-?[\\p{L}0-9 ]+)*", message = "La marca es invalida")
        String brand,

        @NotNull
        @Positive(message = "El atributo cantidad solo puede contener valores positivos")
        @Digits(integer = 19, fraction = 0, message = "El atributo cantidad solo puede contener numeros enteros")
        Integer amount,
        
        @NotNull
        @JsonAlias("unit_price")
        @Positive(message = "El atributo precio solo puede contener valores positivos")
        @Digits(integer = 10, fraction = 2, message = "El atributo precio solo puede contener como máximo 10 valores positivos y 2 decimales")
        BigDecimal unitPrice
    ) {

    }
}
