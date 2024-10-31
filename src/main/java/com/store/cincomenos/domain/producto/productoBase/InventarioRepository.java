package com.store.cincomenos.domain.producto.productoBase;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository <Producto, Long> {

    boolean existsByCodigoDeBarras(String codigoBarras);

    Page<Producto> findByProductoActivoTrue(Pageable paginacion);

    @Query("""
        SELECT p FROM Producto p WHERE p.productoActivo = true 
        AND (:id IS NULL OR p.id = :id)
        AND (:nombre IS NULL OR p.nombre LIKE :nombre) 
        AND (:marca IS NULL OR p.marca LIKE :marca) 
        AND (:categoria IS NULL OR p.categoria = :categoria)                                        
        AND (:precioMin IS NULL OR p.precio >= :precioMin)
        AND (:precioMax IS NULL OR p.precio <= :precioMax)
        AND (:codigoBarras IS NULL OR p.codigoDeBarras LIKE :codigoBarras)
    """)   
    Page<Producto> findByParameters (
        @Param("id") Long id,
        @Param("nombre") String nombre,
        @Param("marca") String marca,
        @Param("categoria") CategoriaProducto categoria, 
        @Param("precioMin") BigDecimal precioMin,
        @Param("precioMax") BigDecimal precioMax,
        @Param("codigoBarras") String codigoBarras,
        Pageable pageable);

    Producto findByCodigoDeBarras(String codigoDeBarras);

    @Modifying
    @Query("""
        UPDATE Producto p 
        SET p.stock = p.stock - :cantidad 
        WHERE p.codigoDeBarras = :codigoDeBarras 
        AND p.stock >= :cantidad
    """)
    void updateStockProducto(String codigoDeBarras, Integer cantidad);

    Page<Producto> getReferenceByCodigoDeBarras(String codigoDeBarras, Pageable paginacion);

}
