package com.store.cincomenos.domain.persona.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.store.cincomenos.domain.dto.persona.customer.DataListCustomers;
import com.store.cincomenos.domain.dto.persona.customer.DataRegisterCustomer;
import com.store.cincomenos.domain.dto.persona.customer.DataResponseCustomer;
import com.store.cincomenos.domain.dto.persona.customer.DataUpdateCustomer;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.console.LogicalDeleteOperationException;

@Service
public class CustomerService {

    @Autowired
    private CustomerRespository respository;

    public Page<DataListCustomers> getList(Long id, Pageable pagination) {
        Page<DataListCustomers> customersList = respository.findById(id, pagination).map(DataListCustomers::new);
        return customersList;
    }
    
    public DataResponseCustomer register(DataRegisterCustomer data) {
        Customer savedCustomer = respository.save(new Customer(data));
        return new DataResponseCustomer(savedCustomer);
    }
    
    public DataResponseCustomer update(DataUpdateCustomer data) {
        Customer customer = respository.findById(data.getId())
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired customer or not exists"));

        customer.updateData(data);
        return new DataResponseCustomer(customer);
    }

    public void logicalDelete(Long id) {
        Customer customer = respository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired attribute or not exists"));

        if(customer.getActiveUser() == false) {
            throw new LogicalDeleteOperationException("The customer is already removed from the product listings");
        }
        
        customer.desableCustomerAccount();
    }

}
