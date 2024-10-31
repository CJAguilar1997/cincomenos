package com.store.cincomenos.domain.factura;

import java.math.BigDecimal;

import com.store.cincomenos.domain.producto.productoBase.Producto;

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
@Table(name = "Items_Facturas")
@Getter
@Setter
@NoArgsConstructor
public class ItemsFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer cantidad;
    @Column(name = "precio_unitario")
    BigDecimal precioUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", referencedColumnName = "id_factura")
    Factura factura;

    public ItemsFactura(Integer cantidad, Producto producto, Factura factura) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.factura = factura;
        this.precioUnitario = producto.getPrecio();
    }

    public BigDecimal getValor() {
        return this.precioUnitario.multiply(new BigDecimal(this.cantidad));
    }
}
