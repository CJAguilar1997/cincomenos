package com.store.cincomenos.domain.persona.employee.jobPosition;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {

    Optional<JobPosition> findByName(String name);

    @Query("""
            SELECT j FROM JobPosition j WHERE :id IS NULL OR j.id = :id 
            AND (:name IS NULL OR j.name = :name)
            """)
    Page<JobPosition> getReferenceByParameters(
        @Param("id") Long id, 
        @Param("name") String name, 
        Pageable pagination);

}
