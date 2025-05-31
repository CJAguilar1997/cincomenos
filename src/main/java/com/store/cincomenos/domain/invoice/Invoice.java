package com.store.cincomenos.domain.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.store.cincomenos.domain.persona.customer.Customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Facturas_Detalle")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Customer customer;

    @Column(name = "fecha_emision")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate registrationDate = LocalDate.now();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura")
    List<InvoiceItems> items = new ArrayList<>();

    @Column(name = "importe_total")
    BigDecimal totalValue = new BigDecimal(0);
    
    public Invoice(Customer customer) {
        this.customer = customer;
    }

    public void setItems(List<InvoiceItems> items) {
        this.items = items;
    }

    public void agregarItems(InvoiceItems item) {
        item.setFactura(this);
        this.items.add(item);
        this.totalValue = this.totalValue.add(item.getValor());
    }

}
