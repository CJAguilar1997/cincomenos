package com.tienda.cincomenos.domain.persona.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Query("""
            SELECT u FROM Usuario u WHERE u.username = :username
            """)
    UserDetails findbyUsername(@Param("username") String username);
    
} 
