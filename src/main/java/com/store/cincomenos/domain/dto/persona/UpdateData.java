package com.store.cincomenos.domain.dto.persona;

public interface UpdateData {
    Long getId();
    String getName();
    String getDni();
    ContactInformationDTO getContactInformationDTO();
}