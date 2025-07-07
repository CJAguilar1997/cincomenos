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
            facturaGuardada.getIssuanceDate(),
            facturaGuardada.getItems().stream()
            .map(item -> {
                BigDecimal valorProductoCantidad = item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getQuantity()));
                return new InvoiceItemsDTO(
                item.getId(),
                item.getQuantity(),
                item.getPrecioUnitario(),
                valorProductoCantidad,
                new ProductDTO(
                    item.getProduct().getBarcode(),
                    item.getProduct().getName(),
                    item.getProduct().getDescription(),
                    item.getProduct().getBrand()
                )
            );
        }).collect(Collectors.toList()),
            facturaGuardada.getTotalValue()
        );
    }
}
