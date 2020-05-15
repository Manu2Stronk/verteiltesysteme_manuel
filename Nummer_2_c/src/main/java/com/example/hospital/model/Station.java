package com.example.hospital.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Station
 */
@Data
@Entity
@NoArgsConstructor
public class Station{
    @Id
    @GeneratedValue
    private Long s_Id;
    private String name;

    @OneToMany(mappedBy = "station")
    private List<Patient> patientenliste;
    private int maxKapazitaet = 0;

    //Konstruktor der Klasse Station
    public Station(String _station_Name, int maxKapa) {
        this.name = _station_Name;
        this.maxKapazitaet = maxKapa;
    }
}