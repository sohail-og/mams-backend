package com.mams.backend.controller;

import com.mams.backend.dto.EquipmentDto;
import com.mams.backend.dto.BaseEquipmentDto;
import com.mams.backend.service.EquipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BASE_COMMANDER', 'LOGISTICS_OFFICER')")
    public ResponseEntity<List<EquipmentDto>> getAllEquipment() {
        return ResponseEntity.ok(equipmentService.getAllEquipmentInventory());
    }

    @GetMapping("/base-wise")
    @PreAuthorize("hasAnyRole('ADMIN', 'BASE_COMMANDER', 'LOGISTICS_OFFICER')")
    public ResponseEntity<List<BaseEquipmentDto>> getBaseWiseEquipment(
            @RequestParam(required = false) Long baseId,
            @RequestParam(required = false) Long equipmentTypeId) {
        return ResponseEntity.ok(equipmentService.getBaseWiseEquipmentInventory(baseId, equipmentTypeId));
    }
}
