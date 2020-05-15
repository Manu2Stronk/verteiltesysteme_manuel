package com.example.hospital.Controller;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/*
    RootController
*/

//Achtung, ohne CrossOrigin wäre kein Zugriff auf die API durch das in Aufgabe 2 c verwendete Dokument möglich. 
@CrossOrigin

@RestController
@RequestMapping("/api")
public class RootController {

    @GetMapping
    public RepresentationModel<?> root() {
        RepresentationModel<?> rootResource = new RepresentationModel<>();
        //HATEOAS und Links zur Patienten- und Station-Übersicht
        rootResource.add(        
            linkTo(methodOn(PatientController.class).getPatienten()).withRel("patients"),
            linkTo(methodOn(StationController.class).getStationen()).withRel("stations"),
            linkTo(methodOn(RootController.class).root()).withSelfRel());
        return rootResource;
    }
}