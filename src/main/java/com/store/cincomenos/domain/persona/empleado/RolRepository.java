package com.store.cincomenos.domain.persona.empleado;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.cincomenos.domain.persona.login.Roles;

@Repository
public interface RolRepository extends JpaRepository<Roles, Long>{
    
    boolean existsByRol(String rol);

    Optional<Roles> findByRol(String rol);

}
