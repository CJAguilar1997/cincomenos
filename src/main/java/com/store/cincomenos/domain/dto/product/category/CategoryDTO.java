package com.store.cincomenos.domain.dto.product.category;

import com.store.cincomenos.domain.product.category.Category;

import jakarta.validation.constraints.Pattern;

public record CategoryDTO(
    @Pattern(regexp = "^$|^[\\p{L} ]{3,20}$", message = "The category name contains invalid characters")
    String name
) {

    public CategoryDTO(Category category) {
        this(category.getName());
    }

    public String name() {
        return (name != null) ? this.name.toUpperCase() : null;
    }
}
