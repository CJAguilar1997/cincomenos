package com.store.cincomenos.domain.dto.invoice;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InvoiceItemsDTO(
    Long id,
    Integer amount,

    @JsonProperty("unit_price")
    BigDecimal unitPrice,

    @JsonProperty("price_value_amount")
    BigDecimal priceValueAmount,

    ProductoDTO product
) {
    

}
