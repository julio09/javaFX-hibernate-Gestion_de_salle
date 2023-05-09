package com.example.m1.Entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "prof")
public class Prof {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codeprof", nullable = false)
    private Long id;

    @Column(name = "nom")
    private String Nom;

    @Column(name = "prenom")

    private String Prenom;


    @Column(name = "grade")
    private String Grade;

    @OneToMany(mappedBy = "prof", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    @Override
    public String toString() {
        return Nom;
    }

}