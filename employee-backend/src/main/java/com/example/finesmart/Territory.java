package com.example.finesmart;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "territories")
public class Territory extends PanacheEntityBase {

    @Id
    @Column(name = "territory_id")
    public String territoryId;

    @Column(name = "territory_description")
    public String description;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable=false, updatable=false)
    Region region;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "<" + territoryId + ">";
    }

}
