package com.store.cincomenos.domain.persona.employee.departament;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentRepository extends JpaRepository<Departament, Long>{

    Optional<Departament> findByName(String name);

    @Query("""
            SELECT d FROM Departament d 
            WHERE :id IS NULL OR d.id = :id 
            AND (:name IS NULL OR d.name = :name)
            """)
    Page<Departament> getReferenceByParameters(
        @Param("id")Long id,
        @Param("name")String name, 
        Pageable pagination);

}
