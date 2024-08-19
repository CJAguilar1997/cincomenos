package com.tienda.cincomenos.domain.persona.empleado;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{

    @Query("""
            SELECT e FROM Empleado e WHERE 
            (:id IS NULL OR e.id = :id) 
            AND (:nombre IS NULL OR e.nombre = :nombre)
            AND (:dni IS NULL OR e.dni = :dni)
            AND (:telefono IS NULL OR e.contacto.telefono = :telefono)
            AND (:fechaDeRegistro IS NULL OR e.fechaRegistro = :fechaDeRegistro)
            """)
    Page<Empleado> getReferenceByParameters(Pageable paginacion, 
        @Param("id") Long id, 
        @Param("nombre") String nombre, 
        @Param("dni") String dni,
        @Param("telefono") String telefono, 
        @Param("fechaDeRegistro") String fechaDeRegistro);

}
