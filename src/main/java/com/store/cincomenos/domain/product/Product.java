package com.store.cincomenos.domain.product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.store.cincomenos.domain.dto.product.DataRegisterProduct;
import com.store.cincomenos.domain.dto.product.DataUpdateProduct;
import com.store.cincomenos.domain.product.attribute.Attribute;
import com.store.cincomenos.domain.product.category.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String barcode;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private Long stock;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Category.class)
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "id_product", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "id_category", referencedColumnName = "id"))
    private Category category;
        
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Attribute.class)
    @JoinTable(name = "product_attributes", joinColumns = @JoinColumn(name = "id_product", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "id_attribute", referencedColumnName = "id"))
    private List<Attribute> attributes;
    
    private LocalDate registrationDate;
    private Boolean activeProduct;
    
    protected Product(DataRegisterProduct datos, Category category) {
        this.barcode = datos.barcode();
        this.name = datos.name();
        this.description = datos.description();
        this.brand = datos.brand();
        this.price = datos.price();
        this.stock = datos.stock();
        this.category = category;
        this.registrationDate = LocalDate.now();
        this.activeProduct = true;
        this.attributes = new ArrayList<>();
    }

    public void desactivarProducto() {
        this.activeProduct = false;
    }

    protected void updateData(@Valid DataUpdateProduct datos, Category category, List<Attribute> attributes) {
        if (datos.barcode() != null && !datos.barcode().isBlank()) {
            this.barcode = datos.barcode();
        }
        if (datos.name() != null && !datos.name().isBlank()) {
            this.name = datos.name();
        }
        if (datos.description() != null && !datos.description().isBlank()) {
            this.description = datos.description();
        }
        if (datos.brand() != null && !datos.brand().isBlank()) {
            this.brand = datos.brand();
        }
        if (datos.price() != null) {
            this.price = datos.price();
        }
        if (category != null) {
            this.category = category;
        }
        if (attributes != null) {
            
            for (Attribute attribute : attributes) {
                if(!this.attributes.contains(attribute)) {
                    this.attributes.add(attribute);
                } else {
                    int index = this.attributes.indexOf(attribute);
                    if (index != -1) {
                        this.attributes.set(index, attribute);
                    }
                }
            }
        }
    }

    public void setAttributes(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public void updateStock(Integer cantidad) {
        this.stock = stock - cantidad;
    }
}
