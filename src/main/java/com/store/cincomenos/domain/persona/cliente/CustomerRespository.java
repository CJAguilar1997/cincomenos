package com.store.cincomenos.domain.persona.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRespository extends JpaRepository<Customer, Long> {

    Page<Customer> findById(Long id, Pageable paginacion);
}
