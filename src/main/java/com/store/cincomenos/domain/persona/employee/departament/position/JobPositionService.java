package com.store.cincomenos.domain.persona.employee.departament.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataListJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataRegisterJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataResponseJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataUpdateJobPosition;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;

import jakarta.validation.Valid;

@Service
public class JobPositionService {

    @Autowired
    private PositionRepository jobPositionRepository;

    public DataResponseJobPosition register(DataRegisterJobPosition data) {
        Position jobPosition = jobPositionRepository.save(new Position(data));
        return new DataResponseJobPosition(jobPosition);
    }

    public Page<DataListJobPosition> getList(Long id, String name, Pageable pagination) {
        Page<DataListJobPosition> reply = jobPositionRepository.getReferenceByParameters(id, name, pagination).map(DataListJobPosition::new);
        return reply;
    }

    public DataResponseJobPosition update(@Valid DataUpdateJobPosition data) {
        Position jobPosition = jobPositionRepository.findById(data.id())
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired job position or not exists"));

        jobPosition.updateData(data);
        return new DataResponseJobPosition(jobPosition);
    }

    public void delete(Long id) {
        Position jobPosition = jobPositionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired job position or not exists"));
        
        jobPositionRepository.delete(jobPosition);
    }

}
