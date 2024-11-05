package com.store.cincomenos.domain.product.category;

import java.util.List;

import com.store.cincomenos.domain.dto.product.category.CategoryDTO;
import com.store.cincomenos.domain.dto.product.category.DataRegisterCategory;
import com.store.cincomenos.domain.dto.product.category.DataUpdateCategory;
import com.store.cincomenos.domain.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "category")
    List<Product> product;

    public Category(CategoryDTO category) {
        this.name = category.name();
    }

    public Category(DataRegisterCategory data) {
        this.name = data.name();
    }

    public void updateData(DataUpdateCategory data) {
        this.name = data.name();
    }
}
