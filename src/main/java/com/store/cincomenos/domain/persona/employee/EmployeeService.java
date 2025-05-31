package com.store.cincomenos.domain.persona.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.store.cincomenos.domain.dto.persona.employee.DataListEmployee;
import com.store.cincomenos.domain.dto.persona.employee.DataRegisterEmployee;
import com.store.cincomenos.domain.dto.persona.employee.DataResponseEmployee;
import com.store.cincomenos.domain.dto.persona.employee.DataResponseEmployeeLogin;
import com.store.cincomenos.domain.dto.persona.employee.DataUpdateEmployee;
import com.store.cincomenos.domain.dto.persona.employee.departament.RegisEmployeeDepartamentDTO;
import com.store.cincomenos.domain.dto.persona.employee.departament.UpdateEmployeeDepartamentDTO;
import com.store.cincomenos.domain.dto.persona.employee.jobPosition.RegisEmployeePositionDTO;
import com.store.cincomenos.domain.dto.persona.login.DataUserLoginResponse;
import com.store.cincomenos.domain.persona.employee.departament.Departament;
import com.store.cincomenos.domain.persona.employee.departament.DepartamentRepository;
import com.store.cincomenos.domain.persona.employee.departament.position.Position;
import com.store.cincomenos.domain.persona.login.User;
import com.store.cincomenos.domain.persona.login.UserRepository;
import com.store.cincomenos.domain.persona.login.role.Role;
import com.store.cincomenos.domain.persona.login.role.RoleRepository;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.console.LogicalDeleteOperationException;
import com.store.cincomenos.infra.exception.console.NullPointerException;
import com.store.cincomenos.utils.user.generator.UserGenerator;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private DepartamentRepository departamentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DeptPosition deptPositionRepository;

    public DataResponseEmployeeLogin register(DataRegisterEmployee data) {
        Employee employee = new Employee(data);

        employeeRepository.save(employee);

        for (RegisEmployeeDepartamentDTO departamentDTO : data.departamentDTO()) {
            boolean containsRepeatedEntities = false;
            if (data.departamentDTO().size() > 1) {
                containsRepeatedEntities = data.departamentDTO().stream()
                    .anyMatch(dept -> data.departamentDTO().stream()
                        .filter(d -> d.name().equals(dept.name()))
                        .count() > 1);
            }
        
            if (containsRepeatedEntities) {
                Map<String, List<RegisEmployeePositionDTO>> departamentsMap = data.departamentDTO().stream()
                    .collect(Collectors.groupingBy(RegisEmployeeDepartamentDTO::name,
                                Collectors.mapping(RegisEmployeeDepartamentDTO::positionDTO, Collectors.toList())));
        
                List<RegisEmployeeDepartamentDTO> updatedDepartaments = new ArrayList<>();
                for (Map.Entry<String, List<RegisEmployeePositionDTO>> entry : departamentsMap.entrySet()) {
                    RegisEmployeePositionDTO combinedPosition = new RegisEmployeePositionDTO(
                            entry.getValue().stream().map(RegisEmployeePositionDTO::name).collect(Collectors.joining(",")));
                    RegisEmployeeDepartamentDTO combinedDepartament = new RegisEmployeeDepartamentDTO(
                            entry.getKey(), combinedPosition);
                    updatedDepartaments.add(combinedDepartament);
            }
            
            data.setDepartamentDTO(updatedDepartaments);
        }
            
            Departament departamentEntity = departamentRepository.findByName(departamentDTO.name())
                .orElseThrow(() -> new EntityNotFoundException("Could not get the desired departament \"" + departamentDTO.name() + "\" or not exists"));
            
            String[] dividedPositions = departamentDTO.positionDTO().name().split(",");
            
            for (String positionR : dividedPositions) {
                Position positionEntity = departamentEntity.getPositions().stream()
                .filter(position -> position.getName().equals(positionR))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Could not get the desired job position \"" + departamentDTO.positionDTO().name() + "\" or not exists"));

                EmployeeDeptPosition edp = deptPositionRepository.save(new EmployeeDeptPosition(employee, departamentEntity, positionEntity));
                employee.addDeptPosition(edp);
            }

        }

        Map<String, String> user = UserGenerator.generate(data.name(), data.contactInformationDTO().email());
        List<Role> roles = getRoles(data.roles());
        String passwordReply = user.get("password");
        user.put("password", passwordEncoder.encode(passwordReply));
        
        User userLogin = userRepository.save(new User(user, roles));
        DataUserLoginResponse loginData = new DataUserLoginResponse(userLogin, passwordReply);
        
        return new DataResponseEmployeeLogin(employee, loginData);
    }
    
    private List<Role> getRoles(Set<String> roles) {
        if (roles.isEmpty()) {
            throw new NullPointerException("No hay roles para añadir al usuario");
        }
        
        List<Role> roleEntities = new ArrayList<>();
        
        for (String role : roles) {
            Role roleEntity = roleRepository.findByRole(role)
                .orElseThrow(() -> new EntityNotFoundException("Could not get the desired rol or not exists"));

            if (roleEntity != null) {
                roleEntities.add(roleEntity);
            }
        }

        return roleEntities;
    }

    public Page<DataListEmployee> getList(Pageable pagination, Long id, String name, String dni, String phoneNumber,
    String registrationDate) {
        Page<DataListEmployee> getListOfEmployee = employeeRepository.getReferenceByParameters(pagination, id, name, dni, phoneNumber, registrationDate).map(DataListEmployee::new);
        return getListOfEmployee;
    }
    
    public DataResponseEmployee update(DataUpdateEmployee data) {
        Employee employee = employeeRepository.findById(data.getId())
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired employee or not exists"));
        
        if (data.getDepartamentDTO() != null) {
            
            for (UpdateEmployeeDepartamentDTO deptDto : data.getDepartamentDTO()) {
                Optional<EmployeeDeptPosition> deptPositionEntity = Optional.ofNullable(null);
                Departament departamentEntity;

                if (deptDto.nameDept() != null) {
                    departamentEntity = departamentRepository.findByName(deptDto.nameDept())
                        .orElseThrow(() -> new EntityNotFoundException("Could not get the desired departament or not exists"));
                } else {
                    departamentEntity = employee.getEmployeeDeptPositions().stream()
                        .filter(edp -> edp.getDepartament().getName().equals(deptDto.nameDeptToUpdate()))
                        .map(EmployeeDeptPosition::getDepartament)
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("The desired departament not exist for this employee"));
                }

                Position positionEntity;

                if (deptDto.positionDTO().namePosition() != null) {
                    positionEntity = departamentEntity.getPositions().stream()
                        .filter(pos -> pos.getName().equals(deptDto.positionDTO().namePosition()))
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("Could not get the desired position or not exists"));
                } else {
                    positionEntity = employee.getEmployeeDeptPositions().stream()
                        .filter(edp -> edp.getPosition().getName().equals(deptDto.positionDTO().namePositionToUpdate()))
                        .map(EmployeeDeptPosition::getPosition)
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("The desired position not exist for this employee"));
                }

                if (deptDto.update().equals("true") || deptDto.positionDTO().update().equals("true")) {
                    boolean exceptionThrown = false;

                    if (deptDto.update().equals("true") && deptDto.type().equals("update")) {
                        try {
                            deptPositionEntity = Optional.of(getDeptPosition(employee, deptDto));
                            deptPositionEntity.ifPresent(edp -> edp.updateDepartament(departamentEntity));
                            
                        } catch (EntityNotFoundException e) {
                            exceptionThrown = true;
                        }
                    }
                    
                    if (deptDto.positionDTO().update().equals("true") && !exceptionThrown) {
                        if (deptPositionEntity == null) {
                            deptPositionEntity = Optional.of(getDeptPosition(employee, deptDto));
                        }
                        
                        deptPositionEntity.ifPresent(edp -> edp.updatePosition(positionEntity)); 
                    }

                    if ((deptDto.type().equals("delete") && deptDto.update().equals("true")) && 
                        ((deptDto.positionDTO().type().equals("delete") && deptDto.positionDTO().update().equals("true")))) {
                        EmployeeDeptPosition edp = deptPositionRepository.findByParameters(employee.getId(), departamentEntity.getId(), positionEntity.getId());
                        employee.getEmployeeDeptPositions().remove(edp);
                        deptPositionRepository.deleteEntity(edp.getId());
                    } else {
                        deptPositionRepository.save(deptPositionEntity.get());
                    }
                    
                    
                } else {

                    employee.getEmployeeDeptPositions().stream()
                        .filter(edp -> edp.getDepartament().equals(departamentEntity) && edp.getPosition().equals(positionEntity))
                        .findFirst()
                        .ifPresent(edp -> {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The departament \"%s\" and \"%s\" already exists for this employee", departamentEntity.getName(), positionEntity.getName()));
                        });
                        
                    deptPositionEntity = Optional.of(new EmployeeDeptPosition(employee, departamentEntity, positionEntity));
                    deptPositionRepository.save(deptPositionEntity.get());
                    employee.addDeptPosition(deptPositionEntity.get());
                }

            }
        }

        employee.updateData(data);
        return new DataResponseEmployee(employee);
    }

    private EmployeeDeptPosition getDeptPosition(Employee employee, UpdateEmployeeDepartamentDTO deptDto) {
        return employee.getEmployeeDeptPositions().stream()
            .filter(edp -> edp.getDepartament().getName().equals(deptDto.nameDeptToUpdate()))
            .filter(edp -> edp.getPosition().getName().equals(deptDto.positionDTO().namePositionToUpdate()))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException(String.format("The departament \"%s\" or \"%s\" not exists for this employee", deptDto.nameDeptToUpdate(), deptDto.positionDTO().namePositionToUpdate())));
    }
    
    public void logicalDelete(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired employee or not exists"));
            
        if(employee.getActiveUser() == false) {
            throw new LogicalDeleteOperationException("The employee is already removed from the product listings");
        }

        employee.deleteEmployeeAccount();

        employeeRepository.save(employee);
    }
    
}
