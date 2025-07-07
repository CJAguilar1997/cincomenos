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
            SELECT i FROM Invoice i WHERE 
            (:id IS NULL OR i.id = :id) 
            AND (:customer IS NULL OR i.customer = :customer) 
            """)
    Page<Invoice> findByParameters(
        @Param("id") Long id, 
        @Param("customer") Customer customer, 
        Pageable pagination);

}
