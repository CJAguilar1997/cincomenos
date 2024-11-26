package com.store.cincomenos.domain.persona.empleado;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    @Query("""
            SELECT e FROM Employee e 
            WHERE e.activeUser = true 
            AND (:id IS NULL OR e.id = :id) 
            AND (:name IS NULL OR e.name = :name)
            AND (:dni IS NULL OR e.dni = :dni)
            AND (:phoneNumber IS NULL OR e.contact.phoneNumber = :phoneNumber)
            AND (:registrationDate IS NULL OR e.registrationDate = :registrationDate)
            """)
    Page<Employee> getReferenceByParameters(Pageable pagination, 
        @Param("id") Long id, 
        @Param("name") String name, 
        @Param("dni") String dni,
        @Param("phoneNumber") String phoneNumber, 
        @Param("registrationDate") String registrationDate);

}
