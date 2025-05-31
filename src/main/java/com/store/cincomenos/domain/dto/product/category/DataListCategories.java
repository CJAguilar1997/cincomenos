package com.store.cincomenos.domain.dto.product.category;

import com.store.cincomenos.domain.product.category.Category;

public record DataListCategories(
    Long id,
    String name
) {

    public DataListCategories(Category category) {
        this(category.getId(), category.getName());
    }
}
