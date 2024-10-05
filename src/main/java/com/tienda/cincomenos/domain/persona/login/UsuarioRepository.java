package com.tienda.cincomenos.domain.persona.login;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Query("""
            SELECT u FROM Usuario u WHERE u.username = :username
            """)
    Optional<Usuario> findbyUsername(@Param("username") String username);
    
} 
