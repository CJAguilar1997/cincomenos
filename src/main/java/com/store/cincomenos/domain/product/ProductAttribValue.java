package com.store.cincomenos.domain.product;

import com.store.cincomenos.domain.product.attribute.Attribute;
import com.store.cincomenos.domain.product.attribute.value.Value;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_attributes_values")
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttribValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id", referencedColumnName = "id")
    private Attribute attribute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "value_id", referencedColumnName = "id")
    private Value value;

    public Attribute getAttribute() {
        return attribute;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public ProductAttribValue(Product product, Attribute attribute, Value value) {
        this.product = product;
        this.attribute = attribute;
        this.value = value;
    }
}
