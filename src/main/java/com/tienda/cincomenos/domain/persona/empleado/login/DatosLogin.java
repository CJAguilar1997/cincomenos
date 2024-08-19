package com.tienda.cincomenos.domain.persona.empleado.login;

import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "Login_Empleados")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DatosLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCuenta;
    String username;
    String password;

    public DatosLogin(Map<String, String> userLogin) {
        this.username = userLogin.get("username");
        this.password = userLogin.get("password");
    }
}
