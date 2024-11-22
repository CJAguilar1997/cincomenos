package com.store.cincomenos.domain.factura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.store.cincomenos.domain.dto.factura.DatosListadoFactura;
import com.store.cincomenos.domain.dto.factura.DatosRegistrarFactura;
import com.store.cincomenos.domain.dto.factura.DatosRespuestaFactura;
import com.store.cincomenos.domain.persona.cliente.Cliente;
import com.store.cincomenos.domain.persona.cliente.ClienteRespository;
import com.store.cincomenos.domain.persona.login.role.product.DataListProducts;
import com.store.cincomenos.domain.product.InventoryRepository;
import com.store.cincomenos.domain.product.Product;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.console.NullPointerException;
import com.store.cincomenos.infra.exception.producto.BarcodeNotExistsException;
import com.store.cincomenos.infra.exception.producto.OutOfStockException;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;
    
    @Autowired
    private InventoryRepository inventarioRepository;

    @Autowired
    private ClienteRespository clienteRespository;

    public DatosRespuestaFactura registrar(DatosRegistrarFactura datos) {
        Cliente cliente = clienteRespository.findById(datos.idCliente())
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired customer or not exists"));

        Factura factura = new Factura(cliente);

        datos.items().forEach(item -> {

            Product producto = inventarioRepository.findByBarcode(item.codigoDeBarras())
                .orElseThrow(() -> new EntityNotFoundException("Could not get the desired product or not exists"));

            if (producto.getStock() < item.cantidad()) {
                throw new OutOfStockException(String.format("No hay existencias suficientes del producto '%s', el stock disponible es de: %d", producto.getName(), producto.getStock()));   
            }
            
            producto.updateStock(item.cantidad());
            ItemsFactura items = new ItemsFactura(item.cantidad(), producto, factura);
            factura.agregarItems(items);
        });

        Factura facturaGuardada = facturaRepository.save(factura);
        return new DatosRespuestaFactura(facturaGuardada);
    }

    public Page<DatosListadoFactura> listarPorParametros(Pageable paginacion, Long id, Long idCliente) {
        Cliente cliente = null;
        if (id == null && idCliente == null) {
            throw new NullPointerException("Se necesitan datos para realizar una busqueda de facturas");
        }
        if(idCliente != null) {
            cliente = clienteRespository.findById(idCliente)
                .orElseThrow(() -> new EntityNotFoundException("Could not get the desired customer or not exists"));
        }
        Page<DatosListadoFactura> listadoFacturas = facturaRepository.findByParameters(id, cliente, paginacion).map(DatosListadoFactura::new);
        return listadoFacturas;
    }

    public Page<DataListProducts> obtenerProducto(String codigoDeBarras, Pageable paginacion) {
        if (!inventarioRepository.existsByBarcode(codigoDeBarras)) {
            throw new BarcodeNotExistsException("El producto no existe en la base de datos");
        }
        Page<DataListProducts> listadoProducto = inventarioRepository.findByBarcode(codigoDeBarras, paginacion).map(DataListProducts::new);
        return listadoProducto;
    }
    
}
