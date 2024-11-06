package com.store.cincomenos.domain.product;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository <Product, Long> {

    boolean existsByBarcode(String barcode);

    Page<Product> findByActiveProductTrue(Pageable pagination);

    @Query("""
        SELECT p FROM Product p 
        LEFT JOIN p.category c 
        WHERE p.activeProduct = true 
        AND (:id IS NULL OR p.id = :id)
        AND (:name IS NULL OR p.name LIKE :name) 
        AND (:brand IS NULL OR p.brand LIKE :brand) 
        AND (:categoryName IS NULL OR c.name = :categoryName)                                        
        AND (:minPrice IS NULL OR p.price >= :minPrice)
        AND (:maxPrice IS NULL OR p.price <= :maxPrice)
        AND (:barcode IS NULL OR p.barcode LIKE :barcode)
    """)   
    Page<Product> findByParameters (
        @Param("id") Long id,
        @Param("name") String name,
        @Param("brand") String brand,
        @Param("categoryName") String category, 
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice,
        @Param("barcode") String barcode,
        Pageable pageable);

    Optional<Product> findByBarcode(String barcode);

    Page<Product> findByBarcode(String barcode, Pageable pagination);

}
