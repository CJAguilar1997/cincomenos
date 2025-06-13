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
    @Min(value = 1, message = "The id must contain at least one number")
    @Max(value = 10, message = "The ID cannot exceed 10 digits.")
    @Positive
    Long idClient,

    List<Product> items
) {
    public record Product(
        @NotBlank
        @JsonAlias("barcode")
        @Pattern(regexp = "[0-9]{6,25}", message = "The barcode must contain at least 6 digits and a maximum of 25.")
        String barcode,

        @NotBlank
        @Pattern(regexp = "[\\p{L}0-9 ]+(\\-?[\\p{L}0-9 ]+)*", message = "The name is invalid")
        String name,

        @Pattern(regexp = "[\\p{L}0-9 ]+(\\-?[\\p{L}0-9 ]+)*", message = "The brand is invalid")
        String brand,

        @NotNull
        @Positive(message = "The quantity attribute can only contain positive values")
        @Digits(integer = 19, fraction = 0, message = "The amount attribute can only contain integers.")
        Integer amount,
        
        @NotNull
        @JsonAlias("unit_price")
        @Positive(message = "The price attribute can only contain positive values")
        @Digits(integer = 10, fraction = 2, message = "The price attribute can only contain a maximum of 10 positive values ​​and 2 decimal places.")
        BigDecimal unitPrice
    ) {

    }
}
