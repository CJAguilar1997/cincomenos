package com.store.cincomenos.domain.dto.persona;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.store.cincomenos.domain.persona.ContactInformation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@JsonPropertyOrder({"phone_number", "email", "address"})
public record ContactInformationDTO(
    @NotBlank
    @JsonAlias({"phone_number", "phone"})
    @Pattern(regexp = "\\+?[0-9 ]+", message = "The phone number contains invalid characters")
    String phoneNumber,

    @Email
    String email,

    @NotBlank
    @Pattern(regexp = "[\\p{L}, ]+", message = "The address constains invalid characters")
    String address
) {

    public ContactInformationDTO(ContactInformation contact) {
        this(contact.getPhoneNumber(), contact.getEmail(), contact.getAddress());
    }

}
