package com.store.cincomenos.domain.dto.persona;

public interface UpdateData {
    Long id();
    String name();
    String dni();
    ContactInformationDTO contactInformationDTO();
}