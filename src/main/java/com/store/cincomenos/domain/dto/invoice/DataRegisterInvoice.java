package com.store.cincomenos.domain.dto.invoice;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DataRegisterInvoice(
    
    @NotNull
    @JsonAlias("id_client")
    @Min(value = 1, message = "The id must contain at least one number")
    @Digits(integer = 12, fraction = 0, message = "Customer id only can contains integer values")
    @Positive
    Long idCustomer,

    List<RegisInvoiceItemDTO> items
) {

    public DataRegisterInvoice(Long idCustomer, List<RegisInvoiceItemDTO> items) {
        this.idCustomer = idCustomer;
        this.items = getListOfInvoiceItems(items);
    }

    private List<RegisInvoiceItemDTO> getListOfInvoiceItems(List<RegisInvoiceItemDTO> items) {
        return items.stream().collect(Collectors.groupingBy(
            RegisInvoiceItemDTO::barcode,
            Collectors.summingInt(RegisInvoiceItemDTO::amount)))
            .entrySet().stream()
            .map(entry -> new RegisInvoiceItemDTO(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }
        
}
