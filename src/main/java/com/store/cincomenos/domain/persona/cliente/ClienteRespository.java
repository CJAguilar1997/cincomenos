package com.store.cincomenos.domain.persona.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRespository extends JpaRepository<Cliente, Long> {

    Page<Cliente> getReferenceById(Long id, Pageable paginacion);
}
