package com.store.cincomenos.domain.dto.persona.cliente;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;
import com.store.cincomenos.domain.persona.cliente.Customer;

@JsonPropertyOrder({"registration_date", "name", "contact_information"})
public record DataListCustomers(
    
    @JsonProperty("registration_date")
    LocalDate registrationDate,

    String name,

    @JsonProperty("contact_information")
    ContactInformationDTO contactInformationDTO
) {

    public DataListCustomers(Customer customer) {
        this(customer.getRegistrationDate(), customer.getName(), new ContactInformationDTO(customer.getContact().getPhoneNumber(), customer.getContact().getEmail(), customer.getContact().getAddress()));
    }
}
