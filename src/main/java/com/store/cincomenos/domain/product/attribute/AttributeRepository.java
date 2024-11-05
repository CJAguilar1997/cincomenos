package com.store.cincomenos.domain.product.attribute;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    boolean existsByName(String name);

    Optional<Attribute> findByName(String name);

    @Query("""
            SELECT a FROM Attribute a WHERE :id IS NULL OR a.id = :id
            AND (:name IS NULL OR a.name = :name)
            """)
    Page<Attribute> findByParameters(
        @Param("id") Long id, 
        @Param("name") String name, 
        Pageable pagination);

}
