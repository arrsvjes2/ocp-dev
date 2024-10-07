package com.example.finesmart;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "region")
public class Region extends PanacheEntityBase {

    @Id
    @Column(name = "region_id")
    public Long regionId;

    @Column(name = "region_description")
    public String description;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "<" + regionId + ">";
    }

}
