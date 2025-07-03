package com.store.cincomenos.domain.dto.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.store.cincomenos.domain.dto.product.attribute.AttributeDTO;
import com.store.cincomenos.domain.dto.product.category.CategoryDTO;
import com.store.cincomenos.domain.product.Product;
import com.store.cincomenos.domain.product.attribute.Attribute;
import com.store.cincomenos.domain.product.category.Category;

@JsonPropertyOrder({"id", "barcode", "name", "description", "brand", "price", "stock", "categories", "attributes"})
public record DataResponseProduct(
    Long id,
    String barcode,
    String name, 
    String description, 
    String brand, 
    BigDecimal price, 
    Long stock, 
    List<CategoryDTO> categories,
    List<AttributeDTO> attributes) {

    public DataResponseProduct(Product product) {
        this(product.getId(), product.getBarcode(), product.getName(), product.getDescription(), product.getBrand(), product.getPrice(), product.getStock(), getCategory(product.getCategory()), getAttribute(product.getAttributes()));
    }

    public BigDecimal price() {
        return this.price.setScale(2);
    }

    private static List<CategoryDTO> getCategory(List<Category> category) {
        return category.stream()
            .map(cat -> new CategoryDTO(cat.getName()))
            .sorted((c1, c2) -> c1.name().compareToIgnoreCase(c2.name()))
            .collect(Collectors.toList());
    }

    private static List<AttributeDTO> getAttribute(List<Attribute> attributes) {
        return attributes.stream()
            .map(attribute -> new AttributeDTO(attribute))
            .sorted((c1, c2) -> c1.name().compareToIgnoreCase(c2.name()))
            .collect(Collectors.toList());
    }

}
