package com.store.cincomenos.domain.product;

import java.math.BigDecimal;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.store.cincomenos.domain.dto.product.DataListProducts;
import com.store.cincomenos.domain.dto.product.DataRegisterProduct;
import com.store.cincomenos.domain.dto.product.DataResponseProduct;
import com.store.cincomenos.domain.dto.product.DataUpdateProduct;
import com.store.cincomenos.domain.dto.product.attribute.AttributeDTO;
import com.store.cincomenos.domain.product.attribute.Attribute;
import com.store.cincomenos.domain.product.attribute.AttributeRepository;
import com.store.cincomenos.domain.product.attribute.value.Value;
import com.store.cincomenos.domain.product.attribute.value.ValueRepository;
import com.store.cincomenos.domain.product.category.Category;
import com.store.cincomenos.domain.product.category.CategoryRepository;
import com.store.cincomenos.infra.exception.responsive.EntityNotFoundException;
import com.store.cincomenos.infra.exception.responsive.NullPointerException;

import jakarta.validation.Valid;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private ValueRepository valueRepository;
    
    public DataResponseProduct register(@Valid DataRegisterProduct datos) {
        Category category = categoryRepository.findByName(datos.category().name())
            .orElseThrow(() -> new DataAccessResourceFailureException("Could not get the desired category"));
        
        Product product = new Product(datos, category);
        
        for (AttributeDTO attribDTO : datos.attributes()) {

            if (!attributeRepository.existsByName(attribDTO.name())) {
                throw new NullPointerException(HttpStatus.BAD_REQUEST, String.format("The attribute %s does not exists", attribDTO.name()));
            }

            Attribute attribute = attributeRepository.findByName(attribDTO.name())
                .orElseThrow(() -> new DataAccessResourceFailureException("Could not get the desired attribute"));

            Value value = valueRepository.save(new Value(attribDTO.value()));

            attribute.setValue(value);
            product.setAttributes(attribute);
        }

        product = inventoryRepository.save(product);
        return new DataResponseProduct(product);
    }
 
    public DataResponseProduct update(@Valid DataUpdateProduct data) {
        if (!inventoryRepository.existsById(data.id())) {
            throw new EntityNotFoundException(HttpStatus.CONFLICT, "The product ID does not exists in the database");
        }
        
        Product producto = inventoryRepository.getReferenceById(data.id());
        Category category = getCategory(data);
        List<Attribute> attributes = getAttribute(data.attributes(), producto.getAttributes());
        
        producto.updateData(data, category, attributes);
        return new DataResponseProduct(producto);
    }
    
    
    public Page<DataListProducts> listByParameters(Pageable pagination, Long id, String name,
    String brand, String category, BigDecimal minPrice, BigDecimal maxPrice, String barcode) {
        Page<DataListProducts> listOfProducts = inventoryRepository.findByParameters(id, name, brand, category, minPrice, maxPrice, barcode, pagination).map(DataListProducts::new);
        return listOfProducts;
    }

    public void logicalDelete(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new EntityNotFoundException(HttpStatus.BAD_REQUEST, "El id no existe en la base de datos");
        } 
        Product producto = inventoryRepository.getReferenceById(id);
        producto.desactivarProducto();
    }

    private Category getCategory(DataUpdateProduct data) {
        Category category = null;
        
        if (data.category().name() != null) {
            if (!categoryRepository.existsByName(data.category().name())) {
                throw new EntityNotFoundException(HttpStatus.CONFLICT, "The category name does exists in the database");
            }
            category = categoryRepository.findByName(data.category().name())
                .orElseThrow(() -> new DataAccessResourceFailureException("Could not get the desired category"));
        }
        return category;
    }

    private List<Attribute> getAttribute(Set<AttributeDTO> attributesDTO, List<Attribute> attributesProduct) {
        List<Attribute> updatedAttributes = new ArrayList<>();

        for (AttributeDTO attribDTO : attributesDTO) {
            Attribute attribute = attributesProduct.stream()
                .filter(attrib -> attrib.getName().equals(attribDTO.name()))
                .findFirst()
                .orElseGet(() -> {
                    Attribute newAttribute = attributeRepository.findByName(attribDTO.name())
                        .orElseThrow(() -> new DataAccessResourceFailureException("Could not get the desired attribute"));
                    return newAttribute;
                });
            
            Value value = attribute.getValue();
            if (value == null) {
                value = valueRepository.save(new Value(attribDTO.value()));
            } else {
                value.updateValue(attribDTO.value());
            }
            attribute.setValue(value);
            updatedAttributes.add(attribute);
        }
        return updatedAttributes;
    }
}
