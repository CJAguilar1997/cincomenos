package com.tienda.cincomenos.domain.factura;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long>{
    
    @Query("""
            SELECT f FROM Factura f WHERE 
            :id IS NULL OR f.id = :id
            """)
    Page<Factura> getReferenceByParameters(@Param("id") Long id, Pageable paginacion);

}
