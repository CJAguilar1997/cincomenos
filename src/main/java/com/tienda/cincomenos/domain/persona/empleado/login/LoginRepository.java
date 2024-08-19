package com.tienda.cincomenos.domain.persona.empleado.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<DatosLogin, Long>{

    
} 
