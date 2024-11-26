package com.store.cincomenos.domain.persona.empleado;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.cincomenos.domain.dto.persona.empleado.DataUpdateEmployee;
import com.store.cincomenos.domain.dto.persona.empleado.DataListEmployee;
import com.store.cincomenos.domain.dto.persona.empleado.DataRegisterEmployee;
import com.store.cincomenos.domain.dto.persona.empleado.DataResponseEmployee;
import com.store.cincomenos.domain.dto.persona.empleado.DataResponseEmployeeLogin;
import com.store.cincomenos.domain.dto.persona.login.DataUserLoginResponse;
import com.store.cincomenos.domain.persona.empleado.jobPosition.JobPosition;
import com.store.cincomenos.domain.persona.empleado.jobPosition.JobPositionRepository;
import com.store.cincomenos.domain.persona.login.User;
import com.store.cincomenos.domain.persona.login.UserRepository;
import com.store.cincomenos.domain.persona.login.role.Role;
import com.store.cincomenos.domain.persona.login.role.RoleRepository;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.console.LogicalDeleteOperationException;
import com.store.cincomenos.infra.exception.console.NullPointerException;
import com.store.cincomenos.utils.user.generator.RoleValidator;
import com.store.cincomenos.utils.user.generator.UserGenerator;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobPositionRepository jobPositionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleValidator roleValidator;

    public DataResponseEmployeeLogin register(DataRegisterEmployee data) {
        roleValidator.validateHierarchy(data.roles());

        JobPosition jobPosition = jobPositionRepository.findByName(data.jobPosition().name())
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired job position or not exists"));
        
        Employee employee = employeeRepository.save(new Employee(data, jobPosition));

        Map<String, String> user = UserGenerator.generate(data.name(), data.contactInformationDTO().email());
        List<Role> roles = getRoles(data.roles());
        String passwordReply = user.get("password");
        user.put("password", passwordEncoder.encode(passwordReply));

        User userLogin = userRepository.save(new User(user, roles));
        DataUserLoginResponse loginData = new DataUserLoginResponse(userLogin, passwordReply);

        return new DataResponseEmployeeLogin(employee, loginData);
    }
    
    public Page<DataListEmployee> getList(Pageable pagination, Long id, String name, String dni, String phoneNumber,
        String registrationDate) {
        Page<DataListEmployee> getListOfEmployee = employeeRepository.getReferenceByParameters(pagination, id, name, dni, phoneNumber, registrationDate).map(DataListEmployee::new);
        return getListOfEmployee;
    }
    
    public DataResponseEmployee update(DataUpdateEmployee data) {
        JobPosition jobPosition = null;

        Employee employee = employeeRepository.findById(data.id())
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired employee or not exists"));

        if (data.jobPosition() != null) {
            jobPosition = jobPositionRepository.findByName(data.jobPosition().name())
                .orElseThrow(() -> new EntityNotFoundException("Could not get the desired workstation or not exists"));
        }    

        employee.updateData(data, jobPosition);
        return new DataResponseEmployee(employee);
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

}
