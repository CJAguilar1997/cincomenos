package com.store.cincomenos.domain.persona.employee.departament;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentRepository extends JpaRepository<Departament, Long>{

    Optional<Departament> findByName(String name);

}
