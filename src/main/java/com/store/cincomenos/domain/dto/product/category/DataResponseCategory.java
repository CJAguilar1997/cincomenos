package com.store.cincomenos.domain.dto.product.category;

import com.store.cincomenos.domain.product.category.Category;

public record DataResponseCategory(
    Long id,
    String name
) {

    public DataResponseCategory(Category category) {
        this(category.getId(), category.getName());
    }

}
