package com.example.hospital.ModelAssembler;

import com.example.hospital.Controller.PatientController;
import com.example.hospital.Controller.RootController;
import com.example.hospital.Controller.StationController;
import com.example.hospital.model.PatientModel;
import com.example.hospital.model.Station;
import com.example.hospital.model.StationModel;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.stream.Collectors;

/*
    Der StationAssembler generiert die Station-Models und reichert diese mit den durch HATEOAS bedingten Links an
*/

//@Service Annotaion ermöglicht, dass im StationController die Klasse verwendet werden kann
@Service
public class StationAssembler extends RepresentationModelAssemblerSupport<Station, StationModel>{
    //Aufruf des super Konstruktors
    public StationAssembler() {
        super(StationController.class, StationModel.class);
    }

    //@Override überschreibt die toModel Methode
    @Override
    public StationModel toModel(Station station){
        StationModel sModel = instantiateModel(station);
        sModel.add(linkTo(methodOn(StationController.class).getStationById(station.getS_Id())).withSelfRel());
        sModel.add(linkTo(methodOn(StationController.class).getPatientenByStation(station.getS_Id())).withRel("Patienten_der_Station"));
        sModel.add(linkTo(methodOn(StationController.class).getStationen()).withRel("stations"));
        sModel.setId(station.getS_Id());
        sModel.setName(station.getName());
        sModel.setMaxKapazitaet(station.getMaxKapazitaet());

        //Abfrage, ob auf einer Station Patienten liegen  
        if(station.getPatientenliste() == null){
            sModel.setPatientenliste(null);
        }
        //HATEAOS Aufbau, falls auf einer Station Patienten liegen
        else{
            sModel.setPatientenliste(station.getPatientenliste().stream()
                .map(patient -> PatientModel.builder().id(patient.getId()).vorname(patient.getVorname()).nachname(patient.getNachname()).build()
                .add(linkTo(methodOn(PatientController.class).getPatienten()).withRel("patienten"))
                .add(linkTo(methodOn(PatientController.class).getPatientById(patient.getId())).withSelfRel())).collect(Collectors.toList()));
        }
        return sModel;
    }
    
    //@Override überschreibt die toCollectionModel Methode
    @Override
    public CollectionModel<StationModel> toCollectionModel(Iterable<? extends Station> entities) {
        CollectionModel<StationModel> stationModels = super.toCollectionModel(entities);
        stationModels.add(linkTo(methodOn(StationController.class).getStationen()).withSelfRel());
        stationModels.add(linkTo(methodOn(RootController.class).root()).withRel("api"));
        return stationModels;
    }
}