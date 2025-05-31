package com.store.cincomenos.domain.persona.employee.departament.position;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    Optional<Position> findByName(String name);

    @Query("""
            SELECT p FROM Position p WHERE :id IS NULL OR p.id = :id 
            AND (:name IS NULL OR p.name = :name)
            """)
    Page<Position> getReferenceByParameters(
        @Param("id") Long id, 
        @Param("name") String name, 
        Pageable pagination);

}
