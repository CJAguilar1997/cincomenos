package com.store.cincomenos.domain.dto.persona.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;
import com.store.cincomenos.domain.persona.cliente.Customer;

@JsonPropertyOrder({"id", "name", "dni", "contact_information"})
public record DataResponseCustomer(
    Long id,
    String name,
    String dni,
    @JsonProperty("contact_information")
    ContactInformationDTO contactInformationDTO
) {

    public DataResponseCustomer(Customer customer) {
        this(customer.getId(), customer.getName(), customer.getDni(), new ContactInformationDTO(customer.getContact().getPhoneNumber(), customer.getContact().getEmail(), customer.getContact().getAddress()));
    }

}
