package com.example.hospital.model;

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
@JsonRootName(value = "patients")
@Relation(collectionRelation = "patients")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientModel extends RepresentationModel<PatientModel>{
    private Long id;
    private String vorname;
    private String nachname;
    private StationModel station;
    private Long st_ID;
}