package com.mams.backend.controller;

import com.mams.backend.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('ADMIN', 'BASE_COMMANDER', 'LOGISTICS_OFFICER')")
    public ResponseEntity<Integer> getAvailableBalance(@RequestParam Long baseId, @RequestParam Long equipmentTypeId) {
        return ResponseEntity.ok(inventoryService.getAvailableBalance(baseId, equipmentTypeId));
    }
}
