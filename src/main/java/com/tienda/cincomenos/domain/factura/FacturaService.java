package com.tienda.cincomenos.domain.factura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tienda.cincomenos.domain.dto.factura.DatosListadoFactura;
import com.tienda.cincomenos.domain.dto.factura.DatosRegistrarFactura;
import com.tienda.cincomenos.domain.dto.factura.DatosRespuestaFactura;
import com.tienda.cincomenos.domain.dto.producto.DatosListadoProducto;
import com.tienda.cincomenos.domain.persona.cliente.Cliente;
import com.tienda.cincomenos.domain.persona.cliente.ClienteRespository;
import com.tienda.cincomenos.domain.producto.productoBase.InventarioRepository;
import com.tienda.cincomenos.domain.producto.productoBase.Producto;

@Service
public class FacturaService {

    @Autowired
    FacturaRepository facturaRepository;
    
    @Autowired
    InventarioRepository inventarioRepository;

    @Autowired
    ClienteRespository clienteRespository;

    public DatosRespuestaFactura registrar(DatosRegistrarFactura datos) {
        Cliente cliente = clienteRespository.getReferenceById(datos.idCliente());
        Factura factura = new Factura(cliente);
        datos.items().forEach(item -> {
            String codigoDeBarras = item.codigoDeBarras();

            if (!inventarioRepository.existsByCodigoDeBarras(codigoDeBarras)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El código de barras no existe");
            }

            Producto producto = inventarioRepository.findByCodigoDeBarras(codigoDeBarras);
            if (producto.getStock() < item.cantidad()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("No hay existencias suficientes del producto '%s', el stock disponible es de: %d", producto.getNombre(), producto.getStock()));   
            }
            
            inventarioRepository.updateStockProducto(item.codigoDeBarras(), item.cantidad());
            ItemsFactura items = new ItemsFactura(item.cantidad(), producto, factura);
            factura.agregarItems(items);
        });
        Factura facturaGuardada = facturaRepository.save(factura);
        return new DatosRespuestaFactura(facturaGuardada);
    }

    public Page<DatosListadoFactura> listarPorParametros(Pageable paginacion, Long id, Long idCliente) {
        Cliente cliente = null;
        if (id == null && idCliente == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Se necesitan datos para realizar una busqueda de facturas");
        }
        if(idCliente != null) {
            if(!clienteRespository.existsById(idCliente)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id del cliente no existe");
            }
            cliente = clienteRespository.getReferenceById(idCliente);
        }
        Page<DatosListadoFactura> listadoFacturas = facturaRepository.getReferenceByParameters(id, cliente, paginacion).map(DatosListadoFactura::new);
        return listadoFacturas;
    }

    public Page<DatosListadoProducto> obtenerProducto(String codigoDeBarras, Pageable paginacion) {
        if (!inventarioRepository.existsByCodigoDeBarras(codigoDeBarras)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto no existe en la base de datos");
        }
        Page<DatosListadoProducto> listadoProducto = inventarioRepository.getReferenceByCodigoDeBarras(codigoDeBarras, paginacion).map(DatosListadoProducto::new);
        return listadoProducto;
    }
    
}
