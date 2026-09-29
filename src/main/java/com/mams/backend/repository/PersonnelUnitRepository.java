package com.mams.backend.repository;

import com.mams.backend.entity.PersonnelUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonnelUnitRepository extends JpaRepository<PersonnelUnit, Long> {
}
