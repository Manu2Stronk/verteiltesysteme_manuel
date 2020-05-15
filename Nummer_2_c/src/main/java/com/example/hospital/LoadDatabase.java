package com.example.hospital;

import com.example.hospital.model.Patient;
import com.example.hospital.model.Station;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.repository.StationRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/*
    Load Database Klasse liefert direkt zu Beginn beim Start der Anwendung vorgefertigte Daten,
    die dann im Nachgang erweitert, bearbeitet und gelöscht werden können
*/

@Configuration
@Slf4j
public class LoadDatabase {   
    @Bean
    CommandLineRunner initLoadRepo(StationRepository stationRepo, PatientRepository patientenRepo) {
        return args ->{
            //erste Station "innere Medizin" anlegen
            Station innere_Medizin = new Station("Innere_Medizin", 23);
            log.info("preloading " + stationRepo.save(innere_Medizin));
            //neue Patienten anlegen und der ersten Station hinzufügen
            log.info("Preloading " + patientenRepo.save(new Patient("Anna", "L.", innere_Medizin)));
            log.info("Preloading " + patientenRepo.save(new Patient("Laurenz", "L.", innere_Medizin)));

            //zweite Station "Chirugie" anlegen
            Station chirugie = new Station("Chirugie", 15);
            log.info("preloading " + stationRepo.save(chirugie));
            //neue Patienten anlegen und der zweiten Station hinzufügen
            log.info("Preloading " + patientenRepo.save(new Patient("Sabine", "K.", chirugie)));
            log.info("Preloading " + patientenRepo.save(new Patient("Marco", "R.", chirugie)));
            };
        }
}