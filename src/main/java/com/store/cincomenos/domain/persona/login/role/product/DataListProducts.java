package com.store.cincomenos.domain.persona.login.role.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.store.cincomenos.domain.persona.login.role.product.attribute.AttributeDTO;
import com.store.cincomenos.domain.persona.login.role.product.category.CategoryDTO;
import com.store.cincomenos.domain.product.Product;
import com.store.cincomenos.domain.product.attribute.Attribute;

public record DataListProducts(
    Long id,
    String barcode,
    String name, 
    String description, 
    String brand, 
    BigDecimal price, 
    Long stock, 
    CategoryDTO category,
    Set<AttributeDTO> attributes
) {

    public DataListProducts(Product product) {
        this(product.getId(), product.getBarcode(), product.getName(), product.getDescription(), product.getBrand(), product.getPrice(), product.getStock(), new CategoryDTO(product.getCategory()), getAttributes(product.getAttributes()));
    }

    private static Set<AttributeDTO> getAttributes(List<Attribute> attributes) {
        return attributes.stream()
            .map(attribute -> new AttributeDTO(attribute))
            .collect(Collectors.toSet());
    }
}
