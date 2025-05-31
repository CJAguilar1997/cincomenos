package com.store.cincomenos.domain.persona.employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.store.cincomenos.domain.dto.persona.employee.DataRegisterEmployee;
import com.store.cincomenos.domain.dto.persona.employee.DataUpdateEmployee;
import com.store.cincomenos.domain.persona.Persona;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmployeeDeptPosition> employeeDeptPositions;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_dismissal/resignation")
    private LocalDate dimissalResignationDate;

    public Employee (DataRegisterEmployee data) {
        super(data);
        this.employeeDeptPositions = new ArrayList<>();
    }

    public void updateData(DataUpdateEmployee data) {
        super.updateData(data);
    }

    public void deleteEmployeeAccount() {
        super.deleteUserAccount();
        this.dimissalResignationDate = LocalDate.now();
    }

    public void addDeptPosition(EmployeeDeptPosition deptPositionEntity) {
        this.employeeDeptPositions.add(deptPositionEntity);
    }
}
