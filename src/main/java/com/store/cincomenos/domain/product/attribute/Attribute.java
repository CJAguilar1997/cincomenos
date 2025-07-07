package com.store.cincomenos.domain.product.attribute;

import java.util.List;

import com.store.cincomenos.domain.dto.product.attribute.DataRegisterAttribute;
import com.store.cincomenos.domain.dto.product.attribute.DataUpdateAttribute;
import com.store.cincomenos.domain.product.ProductAttribValue;

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
@Table(name = "attributes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "attribute")
    private List<ProductAttribValue> productAttribValues;

    public Attribute(DataRegisterAttribute data) {
        this.name = data.name();
    }

    public void updateData(DataUpdateAttribute data) {
        this.name = data.name();
    }
}
