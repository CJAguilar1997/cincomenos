package com.store.cincomenos.domain.persona;

import com.store.cincomenos.domain.dto.persona.ContactInformationDTO;

public interface RegistrationData {
    String name();
    String dni();
    ContactInformationDTO contactInformationDTO();
}
