package com.store.cincomenos.domain.persona.empleado;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.cincomenos.domain.dto.persona.empleado.DatosActualizarEmpleado;
import com.store.cincomenos.domain.dto.persona.empleado.DatosListadoEmpleado;
import com.store.cincomenos.domain.dto.persona.empleado.DatosRegistrarEmpleado;
import com.store.cincomenos.domain.dto.persona.empleado.DatosRespuestaEmpleado;
import com.store.cincomenos.domain.dto.persona.empleado.DatosRespuestaEmpleadoLogin;
import com.store.cincomenos.domain.dto.persona.login.DatosUsuarioLoginRespuesta;
import com.store.cincomenos.domain.persona.login.Roles;
import com.store.cincomenos.domain.persona.login.Usuario;
import com.store.cincomenos.domain.persona.login.UsuarioRepository;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.console.LogicalDeleteOperationException;
import com.store.cincomenos.infra.exception.console.NullPointerException;
import com.store.cincomenos.utils.user.generator.RoleValidator;
import com.store.cincomenos.utils.user.generator.UserGenerator;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleValidator roleValidator;

    public DatosRespuestaEmpleadoLogin registrar(DatosRegistrarEmpleado datos) {
        roleValidator.validateHierarchy(datos.roles());
        
        Empleado respuesta = empleadoRepository.save(new Empleado(datos));

        Map<String, String> usuario = UserGenerator.generate(datos.nombre(), datos.contacto().email());
        Set<Roles> roles = obtenerRoles(datos.roles());
        String passwordResuesta = usuario.get("password");
        usuario.put("password", passwordEncoder.encode(passwordResuesta));

        Usuario loginUsuario = usuarioRepository.save(new Usuario(usuario, roles));
        DatosUsuarioLoginRespuesta datosLogin = new DatosUsuarioLoginRespuesta(loginUsuario, passwordResuesta);

        return new DatosRespuestaEmpleadoLogin(respuesta, datosLogin);
    }
    
    public Page<DatosListadoEmpleado> listar(Pageable paginacion, Long id, String nombre, String dni, String telefono,
    String fechaDeRegistro) {
        Page<DatosListadoEmpleado> listadoEmpleados = empleadoRepository.getReferenceByParameters(paginacion, id, nombre, dni, telefono, fechaDeRegistro).map(DatosListadoEmpleado::new);
        return listadoEmpleados;
    }
    
    public DatosRespuestaEmpleado actualizar(DatosActualizarEmpleado datos) {
        Empleado empleado = empleadoRepository.findById(datos.id())
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired employee or not exists"));
            
        empleado.actualizarDatos(datos);
        return new DatosRespuestaEmpleado(empleado);
    }
    
    public void borrar(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired employee or not exists"));

        if(empleado.getUsuarioActivo() == false) {
            throw new LogicalDeleteOperationException("The employee is already removed from the product listings");
        }

        empleado.borrarCuentaEmpleado();
    }
    
    private Set<Roles> obtenerRoles(Set<String> roles) {
        if (roles.isEmpty()) {
            throw new NullPointerException("No hay roles para añadir al usuario");
        }

        Set<Roles> rolEntities = new HashSet<>();

        for (String rol : roles) {
            Roles roleEntity = rolRepository.findByRol(rol.toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Could not get the desired rol or not exists"));

            if (roleEntity != null) {
                rolEntities.add(roleEntity);
            }
        }
        return rolEntities;
    }

}
