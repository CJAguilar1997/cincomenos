package com.store.cincomenos.domain.factura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.store.cincomenos.domain.dto.factura.DatosListadoFactura;
import com.store.cincomenos.domain.dto.factura.DatosRegistrarFactura;
import com.store.cincomenos.domain.dto.factura.DatosRespuestaFactura;
import com.store.cincomenos.domain.dto.product.DataListProducts;
import com.store.cincomenos.domain.persona.cliente.Cliente;
import com.store.cincomenos.domain.persona.cliente.ClienteRespository;
import com.store.cincomenos.domain.product.InventoryRepository;
import com.store.cincomenos.domain.product.Product;
import com.store.cincomenos.infra.exception.producto.BarcodeNotExistsException;
import com.store.cincomenos.infra.exception.producto.OutOfStockException;
import com.store.cincomenos.infra.exception.responsive.EntityNotFoundException;
import com.store.cincomenos.infra.exception.responsive.NullPointerException;

@Service
public class FacturaService {

    @Autowired
    FacturaRepository facturaRepository;
    
    @Autowired
    InventoryRepository inventarioRepository;

    @Autowired
    ClienteRespository clienteRespository;

    public DatosRespuestaFactura registrar(DatosRegistrarFactura datos) {
        Cliente cliente = clienteRespository.getReferenceById(datos.idCliente());
        Factura factura = new Factura(cliente);
        datos.items().forEach(item -> {
            String codigoDeBarras = item.codigoDeBarras();

            if (!inventarioRepository.existsByBarcode(codigoDeBarras)) {
                throw new BarcodeNotExistsException(HttpStatus.CONFLICT, "El código de barras no existe");
            }

            Product producto = inventarioRepository.findByBarcode(codigoDeBarras);
            if (producto.getStock() < item.cantidad()) {
                throw new OutOfStockException(HttpStatus.CONFLICT, String.format("No hay existencias suficientes del producto '%s', el stock disponible es de: %d", producto.getName(), producto.getStock()));   
            }
            
            inventarioRepository.updateStockProduct(item.codigoDeBarras(), item.cantidad());
            ItemsFactura items = new ItemsFactura(item.cantidad(), producto, factura);
            factura.agregarItems(items);
        });
        Factura facturaGuardada = facturaRepository.save(factura);
        return new DatosRespuestaFactura(facturaGuardada);
    }

    public Page<DatosListadoFactura> listarPorParametros(Pageable paginacion, Long id, Long idCliente) {
        Cliente cliente = null;
        if (id == null && idCliente == null) {
            throw new NullPointerException(HttpStatus.BAD_REQUEST, "Se necesitan datos para realizar una busqueda de facturas");
        }
        if(idCliente != null) {
            if(!clienteRespository.existsById(idCliente)) {
                throw new EntityNotFoundException(HttpStatus.BAD_REQUEST, "El id del cliente no existe");
            }
            cliente = clienteRespository.getReferenceById(idCliente);
        }
        Page<DatosListadoFactura> listadoFacturas = facturaRepository.getReferenceByParameters(id, cliente, paginacion).map(DatosListadoFactura::new);
        return listadoFacturas;
    }

    public Page<DataListProducts> obtenerProducto(String codigoDeBarras, Pageable paginacion) {
        if (!inventarioRepository.existsByBarcode(codigoDeBarras)) {
            throw new BarcodeNotExistsException(HttpStatus.BAD_REQUEST, "El producto no existe en la base de datos");
        }
        Page<DataListProducts> listadoProducto = inventarioRepository.getReferenceByBarcode(codigoDeBarras, paginacion).map(DataListProducts::new);
        return listadoProducto;
    }
    
}
