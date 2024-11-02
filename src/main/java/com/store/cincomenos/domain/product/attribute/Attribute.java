package com.store.cincomenos.domain.product.attribute;

import java.util.List;

import com.store.cincomenos.domain.product.Product;
import com.store.cincomenos.domain.product.attribute.value.Value;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "attributes")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "attributes")
    List<Product> product;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Value.class)
    @JoinTable(name = "attribute_value", joinColumns = @JoinColumn(name = "id_attribute", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "id_value", referencedColumnName = "id"))
    private Value value;

    public void setValue(Value value) {
        this.value = value;
    }
}
