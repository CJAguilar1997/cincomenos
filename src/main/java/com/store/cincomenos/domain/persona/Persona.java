package com.store.cincomenos.domain.persona;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.store.cincomenos.domain.dto.persona.UpdateData;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String dni;

    @Column(name = "active_user")
    private Boolean activeUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Embedded
    private ContactInformation contact;
    
    protected <T extends RegistrationData> Persona(T data) {
        this.name = data.name();
        this.dni = data.dni();
        this.contact = new ContactInformation(data.contactInformationDTO().phoneNumber(), data.contactInformationDTO().email(), data.contactInformationDTO().address());
        this.registrationDate = LocalDate.now();
        this.activeUser = true;
    }

    protected <T extends UpdateData> void updateData(T data) {
        if (data.getName() != null) {
            this.name = data.getName();
        }

        if (data.dni() != null) {
            this.dni = data.dni();
        }

        if (data.contactInformationDTO() != null) {
            if (data.contactInformationDTO().phoneNumber() != null) {
                contact.setPhoneNumber(data.contactInformationDTO().phoneNumber());
            }
            if (data.contactInformationDTO().email() != null) {
                contact.setEmail(data.contactInformationDTO().email());
            }
        }
    }

    protected void deleteUserAccount() {
        this.activeUser = false;
    }

}
