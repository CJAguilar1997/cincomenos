package com.store.cincomenos.domain.persona.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.store.cincomenos.domain.dto.persona.cliente.DatosActualizarCliente;
import com.store.cincomenos.domain.dto.persona.cliente.DatosListadoCliente;
import com.store.cincomenos.domain.dto.persona.cliente.DatosRegistrarCliente;
import com.store.cincomenos.domain.dto.persona.cliente.DatosRespuestaCliente;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.console.LogicalDeleteOperationException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRespository respository;

    public Page<DatosListadoCliente> listar(Long id, Pageable paginacion) {
        Page<DatosListadoCliente> listadoClientes = respository.findById(id, paginacion).map(DatosListadoCliente::new);
        return listadoClientes;
    }
    
    public DatosRespuestaCliente registrar(DatosRegistrarCliente datos) {
        Cliente clienteGuardado = respository.save(new Cliente(datos));
        return new DatosRespuestaCliente(clienteGuardado);
    }
    
    public DatosRespuestaCliente actualizar(DatosActualizarCliente datos) {
        Cliente cliente = respository.findById(datos.id())
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired customer or not exists"));

        cliente.actualizarDatos(datos);
        return new DatosRespuestaCliente(cliente);
    }

    public void borradoLogico(Long id) {
        Cliente cliente = respository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired attribute or not exists"));

        if(cliente.getUsuarioActivo() == false) {
            throw new LogicalDeleteOperationException("The customer is already removed from the product listings");
        }
        
        cliente.desactivarCliente();
    }

}
