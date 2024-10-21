package com.tienda.cincomenos.domain.persona.login;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@Table(name = "Usuarios")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Usuario implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    private String email;
    private String username;
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Roles.class)
    @JoinTable(name = "roles_usuario", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
    Set<Roles> roles;

    public Usuario(Map<String, String> userLogin, Set<Roles> roles) {
        this.email = userLogin.get("email");
        this.username = userLogin.get("username");
        this.password = userLogin.get("password");
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
        .map(rol -> new SimpleGrantedAuthority("ROLE_".concat(rol.getRol())))
        .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return idUsuario + "_" + username;
    }

    public String getPlainUsername() {
        return username;
    }
}
