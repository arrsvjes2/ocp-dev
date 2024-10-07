package com.example.finesmart;

import java.time.Instant;
import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee extends PanacheEntityBase {

    @Id
    @Column(name = "employee_id")
    public Long employeeId;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "first_name")
    public String firstName;

    public String title;

    @Column(name = "title_of_courtesy")
    public String titleOfCourtesy;

    @Column(name = "birth_date")
    public Instant birthDate;

    @Column(name = "hire_date")
    public Instant hireDate;

    public String address;

    public String city;

    @Column(name = "postal_code")
    public String postalCode;

    public String country;

    public String region;

    @Column(name = "home_phone")
    public String homePhone;

    public String extension;

    public byte[] photo;

    @Column(name = "photo_path")
    public String photoPath;

    @Column(length = 1000)
    public String notes;

    @ManyToOne
    @JoinColumn(name = "reports_to", nullable = true, updatable = true)
    public Employee reportsTo;

    @ManyToMany
    @JoinTable(name="employee_territories",
        joinColumns = @JoinColumn(name="employee_id", referencedColumnName="employee_id"),
        inverseJoinColumns = @JoinColumn(name="territory_id", referencedColumnName="territory_id"))
    List<Territory> territories;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "<" + employeeId + ">";
    }


}
