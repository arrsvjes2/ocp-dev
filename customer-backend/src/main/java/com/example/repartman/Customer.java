package com.example.repartman;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "customers")
public class Customer extends PanacheEntityBase {

    @Id
    @Column(name = "customer_id")
    public String customerId;

    @Column(name = "company_name", nullable = false)
    public String companyName;

    @Column(name = "contact_name")
    public String contactName;

    @Column(name = "contact_title")
    public String contactTitle;

    public Address address;

    public String phone;

    public String fax;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "<" + customerId + ">";
    }
}
