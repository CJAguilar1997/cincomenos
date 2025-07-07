package com.store.cincomenos.domain.dto.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.store.cincomenos.domain.dto.product.attribute.AttributeDTO;
import com.store.cincomenos.domain.dto.product.category.CategoryDTO;
import com.store.cincomenos.domain.product.Product;
import com.store.cincomenos.domain.product.ProductAttribValue;
import com.store.cincomenos.domain.product.category.Category;

public record DataListProducts(
    Long id,
    String barcode,
    String name, 
    String description, 
    String brand, 
    BigDecimal price, 
    Long stock, 
    List<CategoryDTO> category,
    List<AttributeDTO> attributes
) {

    public DataListProducts(Product product) {
        this(product.getId(), product.getBarcode(), product.getName(), product.getDescription(), product.getBrand(), product.getPrice(), product.getStock(), getCategory(product.getCategory()), getAttributes(product.getAttributesValues()));
    }

    private static List<CategoryDTO> getCategory(List<Category> category) {
        return category.stream()
            .map(cat -> new CategoryDTO(cat.getName()))
            .collect(Collectors.toList());
    }

    private static List<AttributeDTO> getAttributes(List<ProductAttribValue> attributesValues) {
        return attributesValues.stream()
            .map(attribute -> new AttributeDTO(attribute.getAttribute(), attribute.getValue()))
            .collect(Collectors.toList());
    }
}
