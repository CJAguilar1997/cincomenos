package com.store.cincomenos.domain.persona.customer;

import com.store.cincomenos.domain.dto.persona.customer.DataRegisterCustomer;
import com.store.cincomenos.domain.dto.persona.customer.DataUpdateCustomer;
import com.store.cincomenos.domain.persona.Persona;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Table(name = "customers")
@Getter
@EqualsAndHashCode(callSuper = true)
public class Customer extends Persona{

    public Customer() {

    }
    
    public Customer(DataRegisterCustomer datos) {
        super(datos);
    }

    public void updateData(DataUpdateCustomer data) {
        super.updateData(data); 
    }

    public void desableCustomerAccount() {
        super.deleteUserAccount();
    }
}
