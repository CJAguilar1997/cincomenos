package com.store.cincomenos.domain.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.store.cincomenos.domain.dto.product.DataListProducts;
import com.store.cincomenos.domain.dto.product.DataRegisterProduct;
import com.store.cincomenos.domain.dto.product.DataResponseProduct;
import com.store.cincomenos.domain.dto.product.DataUpdateProduct;
import com.store.cincomenos.domain.dto.product.attribute.AttributeDTO;
import com.store.cincomenos.domain.dto.product.category.CategoryDTO;
import com.store.cincomenos.domain.product.attribute.Attribute;
import com.store.cincomenos.domain.product.attribute.AttributeRepository;
import com.store.cincomenos.domain.product.attribute.value.Value;
import com.store.cincomenos.domain.product.attribute.value.ValueRepository;
import com.store.cincomenos.domain.product.category.Category;
import com.store.cincomenos.domain.product.category.CategoryRepository;
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
    
    public DataResponseProduct register(@Valid DataRegisterProduct data) {
        
        if (inventoryRepository.findByBarcode(data.barcode()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The product is already exists");
        }

        List<Category> categories = getCategory(data.getCategories());
        Product product = new Product(data, categories);
        
        for (AttributeDTO attribDTO : data.attributes()) {

            Attribute attribute = attributeRepository.findByName(attribDTO.name())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not get the desired attribute or not exists"));

            Value value = valueRepository.save(new Value(attribDTO.value()));

            attribute.setValue(value);
            product.setAttributes(attribute);
        }

        product = inventoryRepository.save(product);
        return new DataResponseProduct(product);
    }
 
    public DataResponseProduct update(@Valid DataUpdateProduct data) { 
        Optional<List<Attribute>> attributes = Optional.ofNullable(null);
        Optional<List<Category>> category = Optional.ofNullable(null);

        Product producto = inventoryRepository.findById(data.id())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not get the desired product or not exists"));
            
            if (data.categories() != null) {
                category = Optional.of(getCategory(data.categories()));
            }

            if(data.attributes() != null) {
                attributes = Optional.of(getAttribute(data.attributes(), producto.getAttributes()));
            }
        
            producto.updateData(data, category.orElse(null), attributes.orElse(null));
        return new DataResponseProduct(producto);
    }
    
    public Page<DataListProducts> listByParameters(Pageable pagination, Long id, String name,
    String brand, String category, BigDecimal minPrice, BigDecimal maxPrice, String barcode) {
        Page<DataListProducts> listOfProducts = inventoryRepository.findByParameters(id, name, brand, category, minPrice, maxPrice, barcode, pagination).map(DataListProducts::new);
        return listOfProducts;
    }
    
    public void logicalDelete(Long id) {
        Product producto = inventoryRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Could not get the desired product or not exists"));

        if(producto.getActiveProduct() == false) {
            throw new LogicalDeleteOperationException("The product is already removed from the product listings");
        }

        producto.desactivarProducto();
    }

    //Private Methods

    private List<Category> getCategory(List<CategoryDTO> categoryDTO) {
        List<Category> categoryList = new ArrayList<>();
        
        for (CategoryDTO catDTO : categoryDTO) {
            Category category = categoryRepository.findByName(catDTO.name())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not get the desired category or not exists")); 

            categoryList.add(category); 
        }
        
        return categoryList;
    }

    private List<Attribute> getAttribute(List<AttributeDTO> attributesDTO, List<Attribute> attributesProduct) {
        List<Attribute> updatedAttributes = new ArrayList<>();

        for (AttributeDTO attribDTO : attributesDTO) {
            Attribute attribute = attributesProduct.stream()
                .filter(attrib -> attrib.getName().equals(attribDTO.name()))
                .findFirst()
                .orElseGet(() -> {
                    Attribute newAttribute = attributeRepository.findByName(attribDTO.name())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not get the desired attribute or not exists"));

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
