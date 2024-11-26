package com.store.cincomenos.domain.persona.employee;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.store.cincomenos.domain.dto.persona.employee.DataRegisterEmployee;
import com.store.cincomenos.domain.dto.persona.employee.DataUpdateEmployee;
import com.store.cincomenos.domain.persona.Persona;
import com.store.cincomenos.domain.persona.employee.jobPosition.JobPosition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "employees")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Employee extends Persona{

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = JobPosition.class)
    @JoinTable(name = "employee's_job_position", joinColumns = @JoinColumn(name = "id_employee", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "id_job_position", referencedColumnName = "id"))
    private JobPosition jobPosition;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_dismissal/resignation")
    private LocalDate dimissalResignationDate;

    public Employee (DataRegisterEmployee data, JobPosition jobPosition) {
        super(data);
        this.jobPosition = jobPosition;
    }

    public void updateData(DataUpdateEmployee data, JobPosition jobPosition) {
        super.updateData(data);
        if (data.jobPosition() != null) {
            this.jobPosition = jobPosition;
        }
    }

    public void deleteEmployeeAccount() {
        super.deleteUserAccount();
        this.dimissalResignationDate = LocalDate.now();
    }
}
