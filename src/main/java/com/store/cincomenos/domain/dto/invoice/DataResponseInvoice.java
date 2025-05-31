package com.store.cincomenos.domain.dto.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.store.cincomenos.domain.dto.persona.customer.ClienteDTO;
import com.store.cincomenos.domain.invoice.Invoice;

@JsonPropertyOrder({"id", "registration_date", "customer", "items", "total_value"})
public record DataResponseInvoice(
    Long id,

    @JsonProperty("registration_date")
    LocalDate registrationDate,
    
    ClienteDTO customer,

    List<InvoiceItemsDTO> items,

    @JsonProperty("total_value")
    BigDecimal totalValue
    ) {

    public DataResponseInvoice(Invoice savedInvoice) {
        this(
            savedInvoice.getId(),
            savedInvoice.getRegistrationDate(),
            new ClienteDTO(
                savedInvoice.getCustomer().getId(), 
                savedInvoice.getCustomer().getName(), 
                savedInvoice.getCustomer().getDni(), 
                savedInvoice.getCustomer().getContact().getPhoneNumber()),
            savedInvoice.getItems().stream()
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
            savedInvoice.getTotalValue()
        );
    }
}
