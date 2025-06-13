package com.store.cincomenos.domain.invoice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.store.cincomenos.domain.dto.invoice.DataInvoiceList;
import com.store.cincomenos.domain.dto.invoice.DataRegisterInvoice;
import com.store.cincomenos.domain.dto.invoice.DataResponseInvoice;
import com.store.cincomenos.domain.dto.product.DataListProducts;
import com.store.cincomenos.domain.persona.customer.Customer;
import com.store.cincomenos.domain.persona.customer.CustomerRespository;
import com.store.cincomenos.domain.product.InventoryRepository;
import com.store.cincomenos.domain.product.Product;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.console.NullPointerException;
import com.store.cincomenos.infra.exception.producto.BarcodeNotExistsException;
import com.store.cincomenos.infra.exception.producto.OutOfStockException;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CustomerRespository clientRespository;

    public DataResponseInvoice register(DataRegisterInvoice data) {
        Customer customer = clientRespository.findById(data.idClient())
            .orElseThrow(() -> new EntityNotFoundException("Could not get the desired customer or not exists"));

        Invoice invoice = new Invoice(customer);

        data.items().forEach(item -> {
            Product product = inventoryRepository.findByBarcode(item.barcode())
                .orElseThrow(() -> new EntityNotFoundException("Could not get the desired product or not exists"));

            if (product.getStock() < item.amount()) {
                throw new OutOfStockException(String.format("There is not enough stock of the product '%s', the available stock is: %d", product.getName(), product.getStock()));   
            }
            
            product.updateStock(item.amount());
            InvoiceItems items = new InvoiceItems(item.amount(), product, invoice);
            invoice.agregarItems(items);
        });

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return new DataResponseInvoice(savedInvoice);
    }

    public Page<DataInvoiceList> getByParameters(Pageable pagination, Long id, Long idCustomer) {
        Optional<Customer> customer = Optional.ofNullable(null);
        if ((id == null) && idCustomer == null) {
            throw new NullPointerException("the data is necesary to find an invoice");
        }
        if(idCustomer != null) {
            customer = Optional.of(clientRespository.findById(idCustomer)
                .orElseThrow(() -> new EntityNotFoundException("Could not get the desired customer or not exists")));
        }
        Page<DataInvoiceList> invoiceList = invoiceRepository.findByParameters(id, customer.get(), pagination).map(DataInvoiceList::new);
        return invoiceList;
    }

    public Page<DataListProducts> getProduct(String barcode, Pageable pagination) {
        if (!inventoryRepository.existsByBarcode(barcode)) {
            throw new BarcodeNotExistsException("The product is not exists");
        }
        Page<DataListProducts> productList = inventoryRepository.findByBarcode(barcode, pagination).map(DataListProducts::new);
        return productList;
    }
    
}
