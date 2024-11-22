package com.store.cincomenos.domain.persona.login;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.store.cincomenos.domain.persona.login.role.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String email;
    private String username;
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    List<Role> roles;

    public User(Map<String, String> userLogin, List<Role> roles) {
        this.email = userLogin.get("email");
        this.username = userLogin.get("username");
        this.password = userLogin.get("password");
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
        .map(rol -> new SimpleGrantedAuthority("ROLE_".concat(rol.getRole())))
        .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return id + "_" + username;
    }

    public String getPlainUsername() {
        return username;
    }
}
