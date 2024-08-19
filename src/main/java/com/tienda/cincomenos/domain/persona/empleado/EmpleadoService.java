package com.tienda.cincomenos.domain.persona.empleado;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.domain.dto.persona.empleado.DatosActualizarEmpleado;
import com.tienda.cincomenos.domain.dto.persona.empleado.DatosListadoEmpleado;
import com.tienda.cincomenos.domain.dto.persona.empleado.DatosRegistrarEmpleado;
import com.tienda.cincomenos.domain.dto.persona.empleado.DatosRespuestaEmpleado;
import com.tienda.cincomenos.domain.dto.persona.empleado.DatosRespuestaEmpleadoLogin;
import com.tienda.cincomenos.domain.dto.persona.login.DatosLoginRespuesta;
import com.tienda.cincomenos.domain.persona.empleado.login.DatosLogin;
import com.tienda.cincomenos.domain.persona.empleado.login.LoginRepository;
import com.tienda.cincomenos.utils.user.generator.UserGenerator;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private LoginRepository loginRespository;

    public DatosRespuestaEmpleadoLogin registrar(DatosRegistrarEmpleado datos) {
        Empleado respuesta = empleadoRepository.save(new Empleado(datos));
        Map<String, String> usuario = UserGenerator.generate(datos.nombre());

        DatosLogin loginUsuario = loginRespository.save(new DatosLogin(usuario));
        DatosLoginRespuesta datosLogin = new DatosLoginRespuesta(loginUsuario);

        return new DatosRespuestaEmpleadoLogin(respuesta, datosLogin);
    }

    public Page<DatosListadoEmpleado> listar(Pageable paginacion, Long id, String nombre, String dni, String telefono,
            String fechaDeRegistro) {
        Page<DatosListadoEmpleado> listadoEmpleados = empleadoRepository.getReferenceByParameters(paginacion, id, nombre, dni, telefono, fechaDeRegistro).map(DatosListadoEmpleado::new);
        return listadoEmpleados;
    }

    public DatosRespuestaEmpleado actualizar(DatosActualizarEmpleado datos) {
        Empleado empleado = empleadoRepository.getReferenceById(datos.id());
        System.out.println(datos);
        empleado.actualizarDatos(datos);
        return new DatosRespuestaEmpleado(empleado);
    }

    public void borrar(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Es necesario un id para proceder con el método");
        }
        if (!empleadoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id del usuario no existe");
        }
        Empleado empleado = empleadoRepository.getReferenceById(id);
        empleado.borrarCuentaEmpleado();
    }
}
