package com.tienda.cincomenos.domain.persona.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.domain.dto.cliente.DatosActualizarCliente;
import com.tienda.cincomenos.domain.dto.cliente.DatosListadoCliente;
import com.tienda.cincomenos.domain.dto.cliente.DatosRegistrarCliente;
import com.tienda.cincomenos.domain.dto.cliente.DatosRespuestaCliente;

@Service
public class ClienteService {

    @Autowired
    private ClienteRespository respository;

    public Page<DatosListadoCliente> listar(Long id, Pageable paginacion) {
        if (!respository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id del cliente no existe en la base de datos");
        }
        Page<DatosListadoCliente> listadoClientes = respository.getReferenceById(id, paginacion).map(DatosListadoCliente::new);
        return listadoClientes;
    }
    
    public DatosRespuestaCliente registrar(DatosRegistrarCliente datos) {
        Cliente clienteGuardado = respository.save(new Cliente(datos));
        return new DatosRespuestaCliente(clienteGuardado);
    }
    
    public DatosRespuestaCliente actualizar(DatosActualizarCliente datos) {
        if (!respository.existsById(datos.id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id del cliente no existe en la base de datos");
        }
        Cliente cliente = respository.getReferenceById(datos.id());
        cliente.actualizarDatos(datos);
        return new DatosRespuestaCliente(cliente);

    }

    public void borradoLogico(Long id) {
        if (!respository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id del cliente no existe en la base de datos");
        }
        Cliente cliente = respository.getReferenceById(id);
        cliente.desactivarCliente();
    }

}
