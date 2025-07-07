package com.store.cincomenos.domain.dto.invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
            savedInvoice.getIssuanceDate(),
            getCustomer(savedInvoice),
            getInvoiceItems(savedInvoice),
            savedInvoice.getTotalValue()
        );
    }

    private static List<InvoiceItemsDTO> getInvoiceItems(Invoice savedInvoice) {
        return savedInvoice.getItems().stream()
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
      }).collect(Collectors.toList());
    }

    private static ClienteDTO getCustomer(Invoice savedInvoice) {
        return new ClienteDTO(
            savedInvoice.getCustomer().getId(), 
            savedInvoice.getCustomer().getName(), 
            savedInvoice.getCustomer().getDni(), 
            savedInvoice.getCustomer().getContact().getPhoneNumber());
    }

    public BigDecimal totalValue() {
        return totalValue.setScale(2, RoundingMode.UNNECESSARY);
    }
}
