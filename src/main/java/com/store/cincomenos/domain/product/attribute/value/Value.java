package com.store.cincomenos.domain.product.attribute.value;

import java.util.List;

import com.store.cincomenos.domain.dto.product.attribute.value.ValueDTO;
import com.store.cincomenos.domain.product.ProductAttribValue;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "\"values\"")
public class Value {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String value;

    @OneToMany(mappedBy = "value")
    private List<ProductAttribValue> attribute;

    public Value(ValueDTO value) {
        this.value = value.value();
    }

    public void updateValue(ValueDTO valueDTO) {
        if (valueDTO != null) {
            this.value = valueDTO.value();
        }
    }
}
