package com.example.m1.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "occuper")
public class Occuper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prof_codeprof")
    private Prof prof;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_codesal")
    private Salle salle;

    @Column(name = "date", nullable = false)
    private String date;

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Prof getProf() {
        return prof;
    }

    public void setProf(Prof prof) {
        this.prof = prof;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}