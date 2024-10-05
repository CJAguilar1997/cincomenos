package com.tienda.cincomenos.utils.user.generator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.tienda.cincomenos.infra.exception.console.HierarchyNotContainRoleException;
import com.tienda.cincomenos.infra.exception.console.UnauthorizedRoleException;

public class RoleValidator {

    private RoleHierarchy roleHierarchy;

    public RoleValidator(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

    public void validateHierarchy(Set<String> roleEmployeeRegister, Authentication authenticationUser) {
        List<String> hierarchyRole = roleHierarchy.getHierarchy();
        Set<String> rolesUser = getRolesUser(authenticationUser, hierarchyRole);

        roleEmployeeRegister = roleEmployeeRegister.stream().map(rol -> "ROLE_".concat(rol).toUpperCase()).collect(Collectors.toSet());

        int highestAuthorityRoleIndex = getHighestAuthorityRoleIndex(rolesUser, hierarchyRole);
        int hierarchyRoleEmployeeRegisterIndex = getHighestAuthorityRoleIndex(roleEmployeeRegister, hierarchyRole);

        if(highestAuthorityRoleIndex > 0 && hierarchyRoleEmployeeRegisterIndex <= highestAuthorityRoleIndex) {
            String invalidRoleEmployee = hierarchyRole.get(hierarchyRoleEmployeeRegisterIndex);
            throw new UnauthorizedRoleException(HttpStatus.CONFLICT, String.format("No tienes los permisos para registrar un empleado con el rol %s", invalidRoleEmployee));
        }
    }

    private int getHighestAuthorityRoleIndex(Set<String> roleList, List<String> hierarchyRoleList) {
        return roleList.stream().map(role -> hierarchyRoleList.indexOf(role))
        .filter(index -> index != -1)
        .min(Integer::compare)
        .orElseThrow(() -> new HierarchyNotContainRoleException(HttpStatus.CONFLICT, "No se encontro ningun rol en la jerarquia"));
    }

    private Set<String> getRolesUser(Authentication authenticationUser, List<String> hierarchyRoleList) {
        Set<String> rolesUser = authenticationUser.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toSet());

        rolesUser.stream()
            .filter(role -> !hierarchyRoleList.contains(role))
            .findAny()
            .ifPresent(role -> {
                throw new HierarchyNotContainRoleException(HttpStatus.CONFLICT, String.format("El rol %s no pertenece a la herarquia", role));
            });

        return rolesUser;
    }
}
