package com.example.repartman;

import java.time.Instant;
import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    public Long orderId;

    @Column(name = "customer_id")
    public String customerId;

    @Column(name = "employee_id")
    public Long employeeId;

    @Column(name = "order_date")
    public Instant orderDate;

    @Column(name = "required_date")
    public Instant requiredDate;

    @Column(name = "shipped_date")
    public Instant shippedDate;

    @Column(name = "ship_via")
    public String shipVia;

    @Column(name = "freight")
    public Double freight;

    @Column(name = "ship_name", length = 40)
    public String shipName;

    @Column(name = "ship_address", length = 60)
    public String shipAddress;

    @Column(name = "ship_city", length = 15)
    public String shipCity;

    @Column(name = "ship_region", length = 15)
    public String shipRegion;

    @Column(name = "ship_postal_code", length = 10)
    public String shipPostalCode;

    @Column(name = "ship_country", length = 15)
    public String shipCountry;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    public List<OrderDetails> orderDetails;

    @Override
    public String toString() {
      return this.getClass().getSimpleName() + "<" + orderId + ">";
    }

}
