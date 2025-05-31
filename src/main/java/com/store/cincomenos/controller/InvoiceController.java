package com.store.cincomenos.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.store.cincomenos.domain.dto.invoice.DataInvoiceList;
import com.store.cincomenos.domain.dto.invoice.DataRegisterInvoice;
import com.store.cincomenos.domain.dto.invoice.DataResponseInvoice;
import com.store.cincomenos.domain.dto.product.DataListProducts;
import com.store.cincomenos.domain.invoice.InvoiceService;
import com.store.cincomenos.infra.exception.console.EntityNotFoundException;
import com.store.cincomenos.infra.exception.producto.OutOfStockException;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService service;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> registrarFactura(@Valid @RequestBody DataRegisterInvoice data, UriComponentsBuilder uriComponentsBuilder) {
        try {
            DataResponseInvoice reply = service.register(data);
            URI url = uriComponentsBuilder.path("/invoices/{id}").buildAndExpand(reply.id()).toUri();
            return ResponseEntity.status(HttpStatus.CREATED).location(url).body(reply);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  
        } catch (OutOfStockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());  
        }
    }

    @GetMapping("/getProduct")
    public ResponseEntity<Page<DataListProducts>> getProducts(
        @RequestParam(value = "barcode", required = true) String barcode,
        Pageable pagination) {
            Page<DataListProducts> reply = service.getProduct(barcode, pagination);
            return ResponseEntity.status(HttpStatus.OK).body(reply);
    } 

    @GetMapping
    public ResponseEntity<Page<DataInvoiceList>> listInvoiceByParameters(
        @PageableDefault(size = 30, sort = "id") Pageable pagination,
        @RequestParam(value = "id", required = false) Long id,
        @RequestParam(value = "id_customer", required = false) Long idClient
    ) {
        Page<DataInvoiceList> reply = service.getByParameters(pagination, id, idClient);
        return ResponseEntity.status(HttpStatus.OK).body(reply);
    }
}
