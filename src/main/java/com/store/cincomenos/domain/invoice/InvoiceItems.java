package com.store.cincomenos.domain.invoice;

import java.math.BigDecimal;

import com.store.cincomenos.domain.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoice_items")
@Getter
@Setter
@NoArgsConstructor
public class InvoiceItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer quantity;
    @Column(name = "unit_price")
    BigDecimal precioUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    Invoice invoice;

    public InvoiceItems(Integer quantity, Product product, Invoice invoice) {
        this.quantity = quantity;
        this.product = product;
        this.invoice = invoice;
        this.precioUnitario = product.getPrice();
    }

    public BigDecimal getAmount() {
        return this.precioUnitario.multiply(new BigDecimal(this.quantity));
    }
}
