package com.tienda.cincomenos.domain.factura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.cincomenos.domain.dto.factura.DatosRegistrarFactura;
import com.tienda.cincomenos.domain.dto.factura.DatosRespuestaFactura;
import com.tienda.cincomenos.domain.producto.productoBase.InventarioRepository;
import com.tienda.cincomenos.domain.producto.productoBase.Producto;

@Service
public class FacturaService {

    @Autowired
    FacturaRepository facturaRepository;
    
    @Autowired
    InventarioRepository inventarioRepository;

    public DatosRespuestaFactura registrar(DatosRegistrarFactura datos) {
        Factura factura = new Factura();
        datos.items().forEach(item -> {
            String codigoDeBarras = item.codigoDeBarras();
            Producto producto = inventarioRepository.findByCodigoDeBarras(codigoDeBarras);
            ItemsFactura items = new ItemsFactura(item.cantidad(), producto, factura);
            factura.agregarItems(items);
        });
        Factura facturaGuardada = facturaRepository.save(factura);
        return new DatosRespuestaFactura(facturaGuardada);
    }
    
}
