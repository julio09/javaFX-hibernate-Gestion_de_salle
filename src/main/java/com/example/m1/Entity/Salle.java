package com.example.m1.Entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "salle")
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codesal", nullable = false)
    private Long id;

    @Column(name = "designation", nullable = false)
    private String designation;

    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Occuper> occupers = new LinkedHashSet<>();

    public Set<Occuper> getOccupers() {
        return occupers;
    }

    public void setOccupers(Set<Occuper> occupers) {
        this.occupers = occupers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return designation;
    }
}