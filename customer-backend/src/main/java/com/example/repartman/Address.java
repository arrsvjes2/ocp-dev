package com.example.repartman;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    public String address;

    public String city;

    public String region;

    @Column(name = "postal_code")
    public String postalCode;

    public String country;


}
