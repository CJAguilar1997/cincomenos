package com.tienda.cincomenos.domain.producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository <Producto, Long> {

    boolean existsByCodigoDeBarras(String codigoBarras);

    Page<Producto> findByProductoActivoTrue(Pageable paginacion);

    @Query("""
        SELECT p FROM Producto p WHERE 
        (:id IS NULL OR p.id = :id)
        AND (:nombre IS NULL OR p.nombre LIKE :nombre) 
        AND (:marca IS NULL OR p.marca LIKE :marca) 
        AND (:categoria IS NULL OR p.categoria = :categoria)                                        
        AND (:precioMin IS NULL OR p.precio >= :precioMin)
        AND (:precioMax IS NULL OR p.precio <= :precioMax)
    """)   
    Page<Producto> findByParameters (
        @Param("id") Long id,
        @Param("nombre") String nombre,
        @Param("marca") String marca,
        @Param("categoria") CategoriaProducto categoria, 
        @Param("precioMin") Double precioMin,
        @Param("precioMax") Double precioMax,
        Pageable pageable);

}
