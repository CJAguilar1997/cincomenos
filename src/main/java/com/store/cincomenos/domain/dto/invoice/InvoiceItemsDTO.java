package com.store.cincomenos.domain.dto.invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InvoiceItemsDTO(
    Long id,
    Integer amount,

    @JsonProperty("unit_price")
    BigDecimal unitPrice,

    @JsonProperty("price_value_amount")
    BigDecimal priceValueAmount,

    ProductDTO product
) {

    public BigDecimal priceValueAmount() {
        return priceValueAmount.setScale(2, RoundingMode.UNNECESSARY);
    }

    public BigDecimal unitPrice() {
        return unitPrice.setScale(2, RoundingMode.UNNECESSARY);
    }
    

}
