package com.store.cincomenos.domain.product.category;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    Optional<Category> findByName(String name);

    boolean existsByName(String name);

    @Query("""
            SELECT c FROM Category c WHERE :id IS NULL OR c.id = :id
            AND (:name IS NULL OR c.name = :name)
            """)
    Page<Category> findByParameters(
        @Param("id") Long id, 
        @Param("name") String name, 
        Pageable pagination);

}
