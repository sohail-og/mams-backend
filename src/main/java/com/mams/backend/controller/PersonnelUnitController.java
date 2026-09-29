package com.mams.backend.controller;

import com.mams.backend.entity.PersonnelUnit;
import com.mams.backend.service.PersonnelUnitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/personnel-units")
public class PersonnelUnitController {

    private final PersonnelUnitService service;

    public PersonnelUnitController(PersonnelUnitService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PersonnelUnit>> getAllPersonnelUnits() {
        return ResponseEntity.ok(service.getAllPersonnelUnits());
    }
}
