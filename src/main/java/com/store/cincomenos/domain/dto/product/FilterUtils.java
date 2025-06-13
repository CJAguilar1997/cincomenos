package com.store.cincomenos.domain.dto.product;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.store.cincomenos.domain.dto.product.attribute.AttributeDTO;
import com.store.cincomenos.domain.dto.product.category.CategoryDTO;

public abstract class FilterUtils {

    public static List<CategoryDTO> getFiltredCategories(List<CategoryDTO> categoriesDTO) {
        return categoriesDTO.stream()
            .map(cat -> new CategoryDTO(cat.name().toUpperCase()))
            .collect(Collectors.toMap(
                CategoryDTO::name, 
                Function.identity(),
                (a, b) -> b
            ))
            .values()
            .stream()
            .collect(Collectors.toList());
    }

    public static List<AttributeDTO> getFiltredAttribute(List<AttributeDTO> attributesDTO) {
        return attributesDTO.stream()
            .map(attrib -> new AttributeDTO(attrib.name().toUpperCase(), attrib.value()))
            .collect(Collectors.toMap(
                AttributeDTO::name,
                attrb -> new AttributeDTO(attrb.name(), attrb.value()),
                (a, b) -> b
            ))
            .values()
            .stream()
            .collect(Collectors.toList());
    }
}
