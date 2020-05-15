package com.example.hospital.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.hospital.ModelAssembler.PatientAssembler;
import com.example.hospital.ModelAssembler.StationAssembler;
import com.example.hospital.model.PatientModel;
import com.example.hospital.model.Station;
import com.example.hospital.model.StationModel;
import com.example.hospital.repository.*;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;

/*
  StationController
*/

//Achtung, ohne CrossOrigin wäre kein Zugriff auf die API durch das in Aufgabe 2 c verwendete Dokument möglich. 
@CrossOrigin

@RestController
public class StationController {

//Autowired weil der Assembler als Service durch die @Service Annotation entwickelt wurde - automatische Instanziierung 
@Autowired
private StationAssembler sAss;
@Autowired
private PatientAssembler pAss;

private final StationRepository sRep;

StationController(StationRepository sRep) {
    this.sRep = sRep;
  }

//GetMapping um alle Stationen anzuzeigen
@GetMapping("/stationen")
public CollectionModel<StationModel> getStationen(){
    return sAss.toCollectionModel(sRep.findAll());
}

//GetMapping um eine einzelne Station anzuzeigen
@GetMapping("/stationen/{id}")
public StationModel getStationById(@PathVariable Long id){
    return sAss.toModel(sRep.getOne(id));
}

//GetMapping um eine einzelne Station anzuzeigen
@GetMapping("/stationen/{id}/patienten")
public CollectionModel<PatientModel> getPatientenByStation(@PathVariable Long id){
  Station s1 = sRep.getOne(id);
  return pAss.toCollectionModel(s1.getPatientenliste());
}

//PostMapping Station anlegen
@PostMapping("/stationen")
public ResponseEntity<?> newStation(@RequestBody Station newStation) {
    StationModel stationModel = sAss.toModel(sRep.save(newStation));
    return ResponseEntity.created(stationModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(stationModel);
}

//PutMapping um eine Station zuverändern
@PutMapping("/stationen/{id}")
public ResponseEntity<?> replaceStation(@RequestBody Station newStation, @PathVariable Long id) {
    Station uStation = sRep.findById(id)
        .map(station -> {
            station.setName(newStation.getName());
            station.setMaxKapazitaet(newStation.getMaxKapazitaet());
            station.setPatientenliste(newStation.getPatientenliste());
      return sRep.save(station);
    })

    //falls noch keine Station mit der Id vorliegt
    .orElseGet(() -> {
      newStation.setS_Id(id);
      return sRep.save(newStation);
    });
    StationModel sMod = sAss.toModel(uStation);
    return ResponseEntity.created(sMod.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(sMod);
}

//DeleteMapping um eine bestimmte Station zu löschen
@DeleteMapping("/stationen{id}")
  public ResponseEntity<?> deleteStation(@PathVariable Long id){
    sRep.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}