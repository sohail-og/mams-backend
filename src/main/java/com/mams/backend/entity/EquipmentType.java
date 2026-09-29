package com.mams.backend.entity;

import jakarta.persistence.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "equipment_types")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EquipmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category; // VEHICLE, WEAPON, AMMUNITION

    public EquipmentType() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentType that = (EquipmentType) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
