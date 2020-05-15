package com.example.hospital.ModelAssembler;

import com.example.hospital.Controller.PatientController;
import com.example.hospital.Controller.RootController;
import com.example.hospital.Controller.StationController;
import com.example.hospital.model.Patient;
import com.example.hospital.model.PatientModel;
import com.example.hospital.model.Station;
import com.example.hospital.model.StationModel;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/*
    Der PatientAssembler generiert die Patienten-Models und reichert diese mit den durch HATEOAS bedingten Links an
*/

//@Service Annotaion ermöglicht, dass im PatientController die Klasse verwendet werden kann
@Service
public class PatientAssembler extends RepresentationModelAssemblerSupport <Patient, PatientModel>{
   
    public PatientAssembler() {
        super(PatientController.class, PatientModel.class);
    }

    //@Override überschreibt die toModel Methode
    @Override
    public PatientModel toModel(Patient patient){
        PatientModel pModel= instantiateModel(patient);
        pModel.add(linkTo(methodOn(PatientController.class).getPatientById(patient.getId())).withSelfRel());
        pModel.add(linkTo(methodOn(PatientController.class).getPatienten()).withRel("patienten"));
        pModel.setId(patient.getId());
        pModel.setVorname(patient.getVorname());
        pModel.setNachname(patient.getNachname());

        //Abfrage, ob ein Patient auf einer Station liegt oder keiner Station zugewiesen ist
        Station station = patient.getStation();
        if(station == null){
            pModel.setStation(null);
        }
        //HATEAOS Aufbau, falls eine Station vorhanden ist
        else{
            pModel.setStation(StationModel.builder().id(station.getS_Id()).name(station.getName()).maxKapazitaet(station.getMaxKapazitaet()).build()
            .add(linkTo(methodOn(StationController.class).getStationen()).withRel("stationen"))
            .add(linkTo(methodOn(StationController.class).getStationById(station.getS_Id())).withSelfRel()));
        }
        return pModel;
    }

    //@Override überschreibt die toCollectionModel Methode
    @Override
    public CollectionModel<PatientModel> toCollectionModel(Iterable<? extends Patient> entities) {
        CollectionModel<PatientModel> patientModels = super.toCollectionModel(entities);
        patientModels.add(linkTo(methodOn(PatientController.class).getPatienten()).withSelfRel());
        patientModels.add(linkTo(methodOn(RootController.class).root()).withRel("api"));
        return patientModels;
    }
}