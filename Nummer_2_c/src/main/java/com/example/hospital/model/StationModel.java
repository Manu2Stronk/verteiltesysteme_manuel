package com.example.hospital.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*
    Fertigbaustein zur Anzeige der Station
*/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "stations")
@Relation(collectionRelation = "stations")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StationModel extends RepresentationModel<StationModel>{
    private Long id;    
    private String name;
    private List<PatientModel> patientenliste;
    private int maxKapazitaet;
}