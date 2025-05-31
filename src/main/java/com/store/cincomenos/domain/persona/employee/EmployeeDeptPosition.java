package com.store.cincomenos.domain.persona.employee;

import com.store.cincomenos.domain.persona.employee.departament.Departament;
import com.store.cincomenos.domain.persona.employee.departament.position.Position;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "employee_departaments_positions")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeDeptPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employee", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_departament", referencedColumnName = "id")
    private Departament departament;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_position", referencedColumnName = "id")
    private Position position;
    
    public EmployeeDeptPosition(Employee employee, Departament departamentEntity, Position positionEntity) {
        this.employee = employee;
        this.departament = departamentEntity;
        this.position = positionEntity;
    }

    public void updatePosition(Position positionEntity) {
        this.position = positionEntity;
    }

    public void updateDepartament(Departament departamentEntity) {
        this.departament = departamentEntity;
    }

    
}
