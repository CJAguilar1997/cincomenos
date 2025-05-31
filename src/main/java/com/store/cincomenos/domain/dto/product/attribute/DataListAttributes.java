package com.store.cincomenos.domain.dto.product.attribute;

import com.store.cincomenos.domain.product.attribute.Attribute;

public record DataListAttributes(
    Long id,
    String name
) {

    public DataListAttributes(Attribute attribute) {
        this(attribute.getId(), attribute.getName());
    }

}
