package com.store.cincomenos.domain.persona.login.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    
    boolean existsByRole(String role);

    Optional<Role> findByRole(String role);

}
