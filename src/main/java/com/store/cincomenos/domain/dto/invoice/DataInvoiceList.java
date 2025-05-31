package com.store.cincomenos.domain.dto.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.store.cincomenos.domain.invoice.Invoice;

@JsonPropertyOrder({"id", "fecha_registro", "items", "valor_total"})
public record DataInvoiceList(
    Long id,

    @JsonProperty("fecha_registro")
    LocalDate fechaDeRegistro,
    
    List<InvoiceItemsDTO> items,

    @JsonProperty("valor_total")
    BigDecimal valorTotal
    ) {

    public DataInvoiceList(Invoice facturaGuardada) {
        this(
            facturaGuardada.getId(),
            facturaGuardada.getRegistrationDate(),
            facturaGuardada.getItems().stream()
            .map(item -> {
                BigDecimal valorProductoCantidad = item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad()));
                return new InvoiceItemsDTO(
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
            facturaGuardada.getTotalValue()
        );
    }
}
