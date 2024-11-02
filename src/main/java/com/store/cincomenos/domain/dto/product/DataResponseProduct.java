package com.store.cincomenos.domain.dto.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.store.cincomenos.domain.product.Product;
import com.store.cincomenos.domain.product.attribute.Attribute;

@JsonPropertyOrder({"id", "barcode", "name", "description", "brand", "price", "stock", "category", "attributes"})
public record DataResponseProduct(
    Long id,
    String barcode,
    String name, 
    String description, 
    String brand, 
    BigDecimal price, 
    Long stock, 
    CategoryDTO category,
    Set<AttributeDTO> attributes) {

    public DataResponseProduct(Product product) {
        this(product.getId(), product.getBarcode(), product.getName(), product.getDescription(), product.getBrand(), product.getPrice(), product.getStock(), new CategoryDTO(product.getCategory()), getAttribute(product.getAttributes()));
    }

    private static Set<AttributeDTO> getAttribute(List<Attribute> attributes) {
        return attributes.stream()
            .map(attribute -> new AttributeDTO(attribute))
            .collect(Collectors.toSet());
    }

}
