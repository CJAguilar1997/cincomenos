package com.store.cincomenos.domain.dto.product.attribute;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.store.cincomenos.domain.dto.product.attribute.value.ValueDTO;
import com.store.cincomenos.domain.product.attribute.Attribute;
import com.store.cincomenos.domain.product.attribute.value.Value;

import jakarta.validation.constraints.Pattern;

@JsonPropertyOrder({"name", "value"})
public record AttributeDTO(
    @Pattern(regexp = "^$|^[\\p{L} ]{3,20}$", message = "The attribute name contains invalid characters")
    String name,
    ValueDTO value
) {

    public AttributeDTO(Attribute attribute, Value value) {
        this(attribute.getName(), new ValueDTO(value));
    }

    public String name() {
        return this.name.toUpperCase();
    }

}
