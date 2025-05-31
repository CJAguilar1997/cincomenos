package com.store.cincomenos.domain.persona.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeptPosition extends JpaRepository<EmployeeDeptPosition, Long> {

    @Query("""
            SELECT edp FROM EmployeeDeptPosition edp 
            WHERE edp.employee.id = :id_employee 
            AND (edp.departament.id = :id_departament)
            AND (edp.position.id = :id_position)
        """)
    EmployeeDeptPosition findByParameters(@Param("id_employee") Long employee, 
        @Param("id_departament") Long departamentEntity, 
        @Param("id_position") Long positionEntity);


    @Modifying
    @Query("""
        DELETE FROM EmployeeDeptPosition edp WHERE edp.id = :id
        """)
    void deleteEntity(@Param("id") Long id);

}
