package com.mams.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "personnel_units")
public class PersonnelUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public PersonnelUnit() {}

    public PersonnelUnit(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
