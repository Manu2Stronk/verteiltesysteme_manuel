package com.example.hospital.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Patient
*/

@Data
@Entity
@NoArgsConstructor
public class Patient{
    @Id
    @GeneratedValue
    private Long id;
    private String vorname = "";
    private String nachname = "";
    
    //Spezifiziert die Assoziation zwischen Staion und Patient
    @ManyToOne
    @JoinColumn(name="station_id")
    private Station station;
    private Long st_ID;
    
    //Konstruktor wenn Patient eine Station hat
    public Patient(String _vorname, String _nachname, Station _station) {
        this.vorname  = _vorname;
        this.nachname = _nachname;
        this.station  = _station;
    }

    //Konstruktor wenn Patient eine Stations ID hat -> um später im POST dem Patienten eine Station zu übergeben
    public Patient(String _vorname, String _nachname, Long _st_ID) {
        this.vorname  = _vorname;
        this.nachname = _nachname;
        this.st_ID  = _st_ID;
    }
    
    //Konstruktor wenn Patient keine Station angegeben hat
    public Patient(String _vorname, String _nachname) {
        this.vorname  = _vorname;
        this.nachname = _nachname;
    }
}