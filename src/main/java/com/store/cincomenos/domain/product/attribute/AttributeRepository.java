package com.store.cincomenos.domain.product.attribute;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    boolean existsByName(String name);

    Optional<Attribute> findByName(String name);

}
