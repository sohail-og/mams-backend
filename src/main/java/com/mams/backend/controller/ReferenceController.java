package com.mams.backend.controller;

import com.mams.backend.entity.Base;
import com.mams.backend.entity.EquipmentType;
import com.mams.backend.repository.BaseRepository;
import com.mams.backend.repository.EquipmentTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReferenceController {
    
    private final BaseRepository baseRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;

    public ReferenceController(BaseRepository baseRepository, EquipmentTypeRepository equipmentTypeRepository) {
        this.baseRepository = baseRepository;
        this.equipmentTypeRepository = equipmentTypeRepository;
    }

    @GetMapping("/bases")
    @PreAuthorize("hasAnyRole('ADMIN', 'BASE_COMMANDER', 'LOGISTICS_OFFICER')")
    public ResponseEntity<List<Base>> getBases() {
        return ResponseEntity.ok(baseRepository.findAll());
    }

    @GetMapping("/equipment-types")
    @PreAuthorize("hasAnyRole('ADMIN', 'BASE_COMMANDER', 'LOGISTICS_OFFICER')")
    public ResponseEntity<List<EquipmentType>> getEquipmentTypes() {
        return ResponseEntity.ok(equipmentTypeRepository.findAll());
    }
}
