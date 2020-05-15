package com.example.hospital.repository;

import com.example.hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

/*
    Interface, das Methoden zum Lesen, Updaten, Löschen, erstellen von Patienten zur Verfügung stellt
*/

public interface PatientRepository extends JpaRepository<Patient, Long> {   
}