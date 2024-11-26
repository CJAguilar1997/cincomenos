package com.store.cincomenos.domain.persona.employee.jobPosition;

import java.util.List;

import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataRegisterJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataUpdateJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.JobPositionDTO;
import com.store.cincomenos.domain.persona.employee.Employee;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class JobPosition {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jobPosition")
    private List<Employee> employees;

    public JobPosition(JobPositionDTO jobPositionDTO) {
        this.name = jobPositionDTO.name();
    }

    public JobPosition(DataRegisterJobPosition data) {
        this.name = data.name();
    }

    public void updateData(DataUpdateJobPosition data) {
        if (data.name() != null) {
            this.name = data.name();
        }
    }
}
