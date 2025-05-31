package com.store.cincomenos.domain.persona.employee.departament.position;

import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataRegisterJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataUpdateJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.PositionDTO;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.UpdateEmployeePositionDTO;
import com.store.cincomenos.domain.persona.employee.departament.Departament;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_positions")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Position {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "departament_position", 
        joinColumns = @JoinColumn(name = "id_position", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "id_departament", referencedColumnName = "id"))
    private Departament departament;

    public Position(PositionDTO positionDTO) {
        this.name = positionDTO.name();
    }

    public Position(DataRegisterJobPosition data) {
        this.name = data.name();
    }

    public void updateData(DataUpdateJobPosition data) {
        if (data.name() != null) {
            this.name = data.name();
        }
    }

    public Position(UpdateEmployeePositionDTO positionDTO) {
        this.name = positionDTO.namePosition();
    }
}
