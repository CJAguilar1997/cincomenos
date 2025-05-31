package com.store.cincomenos.domain.invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.store.cincomenos.domain.persona.customer.Customer;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{
    
    @Query("""
            SELECT f FROM Invoice f WHERE 
            (:id IS NULL OR f.id = :id) 
            AND (:idClient IS NULL OR f.customer = :idCustomer) 
            """)
    Page<Invoice> findByParameters(
        @Param("id") Long id, 
        @Param("idCustomer") Customer customer, 
        Pageable pagination);

}
