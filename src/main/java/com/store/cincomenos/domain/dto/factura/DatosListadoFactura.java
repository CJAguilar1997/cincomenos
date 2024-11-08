package com.store.cincomenos.domain.dto.factura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.store.cincomenos.domain.factura.Factura;

@JsonPropertyOrder({"id", "fecha_registro", "items", "valor_total"})
public record DatosListadoFactura(
    Long id,

    @JsonProperty("fecha_registro")
    LocalDate fechaDeRegistro,
    
    List<ItemsFacturaDTO> items,

    @JsonProperty("valor_total")
    BigDecimal valorTotal
    ) {

    public DatosListadoFactura(Factura facturaGuardada) {
        this(
            facturaGuardada.getId(),
            facturaGuardada.getFechaDeRegistro(),
            facturaGuardada.getItems().stream()
            .map(item -> {
                BigDecimal valorProductoCantidad = item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad()));
                return new ItemsFacturaDTO(
                item.getId(),
                item.getCantidad(),
                item.getPrecioUnitario(),
                valorProductoCantidad,
                new ProductoDTO(
                    item.getProducto().getBarcode(),
                    item.getProducto().getName(),
                    item.getProducto().getDescription(),
                    item.getProducto().getBrand()
                )
            );
        }).collect(Collectors.toList()),
            facturaGuardada.getValorTotal()
        );
    }
}
