package com.store.cincomenos.domain.persona.employee.departament.position;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataListJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataRegisterJobPosition;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.DataResponseJobPosition;
import com.store.cincomenos.domain.dto.position.PositionFilterDTO;

@Service
public class JobPositionService {

    @Autowired
    private PositionRepository jobPositionRepository;

    public DataResponseJobPosition register(DataRegisterJobPosition data) {
        Optional<Position> position = Optional.ofNullable(null);
        try {
            position = Optional.of(jobPositionRepository.save(new Position(data)));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The departament \"%s\" already exists", data.name()));
        }
        return new DataResponseJobPosition(position.get());
    }

    public Page<DataListJobPosition> getList(PositionFilterDTO positionFilterDTO, Pageable pagination) {
        Page<DataListJobPosition> reply = jobPositionRepository.getReferenceByParameters(positionFilterDTO.id(), positionFilterDTO.name(), pagination).map(DataListJobPosition::new);
        return reply;
    }

    /*public DataResponseJobPosition update(@Valid DataUpdateJobPosition data) {
        Position jobPosition = jobPositionRepository.findById(data.id())
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired job position or not exists"));

        jobPosition.updateData(data);
        return new DataResponseJobPosition(jobPosition);
    }

    public void delete(Long id) {
        Position jobPosition = jobPositionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired job position or not exists"));
        
        jobPositionRepository.delete(jobPosition);
    }*/

}
