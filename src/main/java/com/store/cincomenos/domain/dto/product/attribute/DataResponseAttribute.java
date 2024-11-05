package com.store.cincomenos.domain.dto.product.attribute;

import com.store.cincomenos.domain.product.attribute.Attribute;

public record DataResponseAttribute(
    Long id,
    String name
) {

    public DataResponseAttribute(Attribute attribute) {
        this(attribute.getId(), attribute.getName());
    }

}
