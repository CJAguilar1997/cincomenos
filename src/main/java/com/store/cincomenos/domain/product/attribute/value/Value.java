package com.store.cincomenos.domain.product.attribute.value;

import com.store.cincomenos.domain.persona.login.role.product.attribute.value.ValueDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public Value(ValueDTO value) {
        this.value = value.value();
    }

    public void updateValue(ValueDTO valueDTO) {
        if (valueDTO != null) {
            this.value = valueDTO.value();
        }
    }
}
