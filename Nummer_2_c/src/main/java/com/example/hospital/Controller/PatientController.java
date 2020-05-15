package com.example.hospital.Controller;

import com.example.hospital.ModelAssembler.PatientAssembler;
import com.example.hospital.model.Patient;
import com.example.hospital.model.PatientModel;
import com.example.hospital.model.Station;
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
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;

/*
  Die PatientenController Klasse kümmert sich um die Abfragen
  rund um den Patienten
*/

//Achtung, ohne CrossOrigin wäre kein Zugriff auf die API durch das in Aufgabe 2 c verwendete Dokument möglich. 
@CrossOrigin

//Controller
@RestController
public class PatientController {

//Patientenrepository, in dem die Patienten gespeichert werden
private final PatientRepository pRep;
//Stationrepository, da beim Post im Stationreopsitory geschaut wird, welche Station sich hinter der mitgegebenen ID verbirgt
private final StationRepository sRep;

//Patientenasembler
@Autowired
private PatientAssembler pAss;

PatientController(PatientRepository pRep, StationRepository sRep) {
  this.pRep = pRep;
  this.sRep = sRep;
}    

//GetMapping um alle Patienten anzeigen
@GetMapping("/patienten")
public CollectionModel<PatientModel> getPatienten(){
  return pAss.toCollectionModel(pRep.findAll());
}

//GetMapping um einen einzelnen Patienten anzuzeigen
@GetMapping("/patienten/{id}")
public PatientModel getPatientById(@PathVariable Long id){
  return pAss.toModel(pRep.getOne(id));
}

//PostMapping um einen neuen Patienten anzulegen
@PostMapping("/patienten")
public ResponseEntity<?> newPatient(@RequestBody Patient newPatient) {
  //Herausfinden, welche ID die Station des neuen Patienten hat
  Long sHelp = newPatient.getSt_ID();
  //im Station Repository schauen, welche Station sich unter sHelp verbirgt
  Station stHelp = sRep.getOne(sHelp);
  newPatient.setStation(stHelp);
  PatientModel patientModel = pAss.toModel(pRep.save(newPatient));
  return ResponseEntity.created(patientModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(patientModel);
}

//PutMapping um einen bestimmten Patienten anzupassen
@PutMapping("/patienten/{id}")
public ResponseEntity<?> replacePatient(@RequestBody Patient newPatient, @PathVariable Long id) {
  Patient uPatient = pRep.findById(id)
    .map(patient -> {
      patient.setVorname(newPatient.getVorname());
      patient.setNachname(newPatient.getNachname());
      Long sID_help = newPatient.getSt_ID();
      
      if(sID_help != null){
      Station sSave = sRep.getOne(sID_help);
      patient.setStation(sSave);
      }
      return pRep.save(patient);
    })
    .orElseGet(() -> {
      newPatient.setId(id);
      return pRep.save(newPatient);
    });
    PatientModel pMod = pAss.toModel(uPatient);
    return ResponseEntity.created(pMod.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(pMod);
  }

//DeleteMapping um einen bestimmten Patienten zu löschen
@DeleteMapping("/patienten/{id}")
  public ResponseEntity<?> deletePatient(@PathVariable Long id){
    pRep.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}