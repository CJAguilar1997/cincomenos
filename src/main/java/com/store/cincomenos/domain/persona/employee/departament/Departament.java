package com.store.cincomenos.domain.persona.employee.departament;

import java.util.List;

import com.store.cincomenos.domain.persona.employee.departament.position.Position;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departaments")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Departament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "departament_position", joinColumns = @JoinColumn(name = "id_departament", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "id_position", referencedColumnName = "id"))
    private List<Position> positions;

    public Departament(Departament departamentEntity, List<Position> positions) {
        this.id = departamentEntity.getId();
        this.name = departamentEntity.getName();
        this.positions = positions;
    }

    public void addPosition(Position position) {
        this.positions.add(position);
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}
