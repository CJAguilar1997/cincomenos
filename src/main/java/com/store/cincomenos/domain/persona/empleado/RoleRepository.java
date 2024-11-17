package com.store.cincomenos.domain.persona.empleado;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.cincomenos.domain.persona.login.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    
    boolean existsByRole(String role);

    Optional<Role> findByRole(String role);

}
