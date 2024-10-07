package com.example.repartman;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "order_details")
public class OrderDetails extends PanacheEntityBase {

    @Id
    @Column(name = "product_id")
    public String productId;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    public Order order;

    @Column(name = "unit_price")
    public BigDecimal unitPrice;

    @Column(name = "quantity")
    public short quantity;

    @Column(name = "discount")
    public BigDecimal discount;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "<" + productId + ">";
    }

}
