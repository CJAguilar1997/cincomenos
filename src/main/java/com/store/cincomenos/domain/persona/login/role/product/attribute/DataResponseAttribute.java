package com.store.cincomenos.domain.persona.login.role.product.attribute;

import com.store.cincomenos.domain.product.attribute.Attribute;

public record DataResponseAttribute(
    Long id,
    String name
) {

    public DataResponseAttribute(Attribute attribute) {
        this(attribute.getId(), attribute.getName());
    }

}
