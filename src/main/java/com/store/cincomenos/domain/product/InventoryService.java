package com.store.cincomenos.domain.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.store.cincomenos.domain.persona.login.role.product.DataListProducts;
import com.store.cincomenos.domain.persona.login.role.product.DataRegisterProduct;
import com.store.cincomenos.domain.persona.login.role.product.DataResponseProduct;
import com.store.cincomenos.domain.persona.login.role.product.DataUpdateProduct;
import com.store.cincomenos.domain.persona.login.role.product.attribute.AttributeDTO;
import com.store.cincomenos.domain.product.attribute.Attribute;
import com.store.cincomenos.domain.product.attribute.AttributeRepository;
import com.store.cincomenos.domain.product.attribute.value.Value;
import com.store.cincomenos.domain.product.attribute.value.ValueRepository;
import com.store.cincomenos.domain.product.category.Category;
import com.store.cincomenos.domain.product.category.CategoryRepository;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.console.LogicalDeleteOperationException;

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
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired product or not exists"));

        Product product = new Product(datos, category);
        
        for (AttributeDTO attribDTO : datos.attributes()) {

            Attribute attribute = attributeRepository.findByName(attribDTO.name())
                .orElseThrow(() -> new EntityNotFoundException("Could not get the desired product or not exists"));

            Value value = valueRepository.save(new Value(attribDTO.value()));

            attribute.setValue(value);
            product.setAttributes(attribute);
        }

        product = inventoryRepository.save(product);
        return new DataResponseProduct(product);
    }
 
    public DataResponseProduct update(@Valid DataUpdateProduct data) {     
        Product producto = inventoryRepository.findById(data.id())
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired product or not exists"));
            
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
        Product producto = inventoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired product or not exists"));

        if(producto.getActiveProduct() == false) {
            throw new LogicalDeleteOperationException("The product is already removed from the product listings");
        }

        producto.desactivarProducto();
    }

    //Private Methods

    private Category getCategory(DataUpdateProduct data) {
        Category category = null;
        
        if (data.category().name() != null) {
            category = categoryRepository.findByName(data.category().name())
                .orElseThrow(() -> new EntityNotFoundException("Could not get the desired category or not exists"));
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
                        .orElseThrow(() -> new EntityNotFoundException("Could not get the desired attribute or not exists"));

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
