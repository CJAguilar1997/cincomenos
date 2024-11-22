package com.store.cincomenos.domain.persona.login.role.product.category;

import com.store.cincomenos.domain.product.category.Category;

public record DataResponseCategory(
    Long id,
    String name
) {

    public DataResponseCategory(Category category) {
        this(category.getId(), category.getName());
    }

}
