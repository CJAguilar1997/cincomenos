package com.store.cincomenos.domain.factura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.store.cincomenos.domain.persona.cliente.Customer;

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
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Customer cliente;

    @Column(name = "fecha_emision")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fechaDeRegistro = LocalDate.now();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura")
    List<ItemsFactura> items = new ArrayList<>();

    @Column(name = "importe_total")
    BigDecimal valorTotal = new BigDecimal(0);
    
    public Factura(Customer cliente) {
        this.cliente = cliente;
    }

    public void setItems(List<ItemsFactura> items) {
        this.items = items;
    }

    public void agregarItems(ItemsFactura item) {
        item.setFactura(this);
        this.items.add(item);
        this.valorTotal = this.valorTotal.add(item.getValor());
    }

}
