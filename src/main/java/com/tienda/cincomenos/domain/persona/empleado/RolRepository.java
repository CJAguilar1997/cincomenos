package com.tienda.cincomenos.domain.persona.empleado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.cincomenos.domain.persona.login.ERoles;
import com.tienda.cincomenos.domain.persona.login.Roles;

@Repository
public interface RolRepository extends JpaRepository<Roles, Long>{
    
    boolean existsByRol(String rol);

    Roles findByRol(ERoles eRoles);

}
