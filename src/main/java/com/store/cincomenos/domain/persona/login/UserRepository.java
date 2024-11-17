package com.store.cincomenos.domain.persona.login;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query("""
            SELECT u FROM User u WHERE u.username = :username
            """)
    Optional<User> findByUsername(@Param("username") String username);

    @Query("""
            SELECT u FROM User u WHERE u.email = :email
            """)
    Optional<User> findByEmail(@Param("email") String email);

    @Query("""
            SELECT u FROM User u WHERE u.id = :id AND u.username = :username
            """)
    Optional<User> findByIdAndUsername(@Param("id") String id, @Param("username") String username);
    
} 
