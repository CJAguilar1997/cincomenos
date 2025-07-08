package com.store.cincomenos.domain.dto.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record ProductFilterDTO(
    @Min(1)
    @Positive
    @Digits(integer = 12, fraction = 0, message = "The id value can has more of 12 digits and can has fractions")
    Long id,

    @Pattern(regexp = "([\\p{L} ]{3,50})?")
    String name,
    
    @Pattern(regexp = "([\\p{L} ]{3,50})?")
    String brand,
    
    @Pattern(regexp = "([\\p{L} ]{3,50})?")
    String category,
    
    @Positive
    @Digits(integer = 12, fraction = 2)
    BigDecimal minPrice,
    
    @Positive
    @Digits(integer = 12, fraction = 2)
    BigDecimal maxPrice,

    @Pattern(regexp = "([0-9]{6,25}+)?")
    String barcode
) {

    public String category() {
        return (category != null) ? category.toUpperCase() : null;
    }

    
}
