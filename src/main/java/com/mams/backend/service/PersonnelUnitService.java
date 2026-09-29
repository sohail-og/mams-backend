package com.mams.backend.service;

import com.mams.backend.entity.PersonnelUnit;
import com.mams.backend.repository.PersonnelUnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonnelUnitService {
    private final PersonnelUnitRepository repository;

    public PersonnelUnitService(PersonnelUnitRepository repository) {
        this.repository = repository;
    }

    public List<PersonnelUnit> getAllPersonnelUnits() {
        return repository.findAll();
    }
}
