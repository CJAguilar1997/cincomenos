package com.store.cincomenos.domain.persona.employee.departament;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.store.cincomenos.domain.dto.departament.DataListDepartament;
import com.store.cincomenos.domain.dto.departament.DataRegisterDepartament;
import com.store.cincomenos.domain.dto.departament.DataResponseDepartament;
import com.store.cincomenos.domain.dto.departament.DepartamentFilterDTO;

@Service
public class DepartamentService {

    @Autowired
    private DepartamentRepository departamentRepository;

    public DataResponseDepartament register(DataRegisterDepartament data) {
        Optional<Departament> departament = Optional.ofNullable(null);
        try {
            departament = Optional.of(departamentRepository.save(new Departament(data)));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The departament \"%s\" already exists", data.name()));
        }
        return new DataResponseDepartament(departament.get());
    }
    
    public Page<DataListDepartament> getList(DepartamentFilterDTO departamentFilterDTO, Pageable pagination) {
        Page<DataListDepartament> reply = departamentRepository.getReferenceByParameters(departamentFilterDTO.id(), departamentFilterDTO.name(), pagination).map(DataListDepartament::new);
        return reply;
    }
}
