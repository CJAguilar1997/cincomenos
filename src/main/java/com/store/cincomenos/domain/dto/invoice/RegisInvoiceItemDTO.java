package com.store.cincomenos.domain.dto.invoice;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record RegisInvoiceItemDTO(
    @NotBlank
    @Pattern(regexp = "[0-9]+")
    String barcode,

    @NotNull
    @Min(value = 1, message = "Amount needs at least one value")
    @Digits(integer = 25, fraction = 0, message = "Amount just can contain integer values")
    @Positive(message = "Amount only can contains positive numbers")
    Integer amount
) {

}
