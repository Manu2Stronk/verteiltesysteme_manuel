package com.example.hospital.Controller;

/*
    Fehlerklasse, falls keine Entität gefunden werden kann
*/
public class EntityNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String problem_entity, Long id) {
        super("Keine Entität gefunden, die zu folgenden Parametern passt : "+ problem_entity + id);
    }
}