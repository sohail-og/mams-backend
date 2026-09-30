package com.mams.backend.service;

import com.mams.backend.dto.DashboardMetricsResponse;
import com.mams.backend.entity.Base;
import com.mams.backend.entity.EquipmentType;
import com.mams.backend.entity.Inventory;
import com.mams.backend.exception.InsufficientStockException;
import com.mams.backend.repository.BaseRepository;
import com.mams.backend.repository.EquipmentTypeRepository;
import com.mams.backend.repository.InventoryRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BaseRepository baseRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;
    private final DashboardService dashboardService;

    public InventoryService(InventoryRepository inventoryRepository, BaseRepository baseRepository, EquipmentTypeRepository equipmentTypeRepository, @Lazy DashboardService dashboardService) {
        this.inventoryRepository = inventoryRepository;
        this.baseRepository = baseRepository;
        this.equipmentTypeRepository = equipmentTypeRepository;
        this.dashboardService = dashboardService;
    }

    public int getAvailableBalance(Long baseId, Long equipmentTypeId) {
        DashboardMetricsResponse metrics = dashboardService.getMetrics(baseId, equipmentTypeId, null, null);
        return metrics.getClosingBalance();
    }

    @Transactional
    public void addInventory(Long baseId, Long equipmentTypeId, Integer quantity) {
        Inventory.InventoryId id = new Inventory.InventoryId(baseId, equipmentTypeId);
        Inventory inventory = inventoryRepository.findById(id).orElseGet(() -> {
            Base base = baseRepository.findById(baseId).orElseThrow();
            EquipmentType type = equipmentTypeRepository.findById(equipmentTypeId).orElseThrow();
            return new Inventory(base, type, 0);
        });
        
        // Optionally sync it strictly with real balance:
        int available = getAvailableBalance(baseId, equipmentTypeId);
        inventory.setQuantity(available + quantity);
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void deductInventory(Long baseId, Long equipmentTypeId, Integer quantity) {
        Base base = baseRepository.findById(baseId).orElseThrow(() -> new IllegalArgumentException("Base not found"));
        EquipmentType type = equipmentTypeRepository.findById(equipmentTypeId).orElseThrow(() -> new IllegalArgumentException("Equipment not found"));
        
        int available = getAvailableBalance(baseId, equipmentTypeId);
        
        if (available == 0) {
            throw new InsufficientStockException("No inventory available for " + type.getName() + " at " + base.getName() + ".");
        }
        
        if (available < quantity) {
            throw new InsufficientStockException("Insufficient inventory. " + base.getName() + " has only " + available + " units of " + type.getName() + " available.");
        }
        
        Inventory.InventoryId id = new Inventory.InventoryId(baseId, equipmentTypeId);
        Inventory inventory = inventoryRepository.findById(id).orElseGet(() -> new Inventory(base, type, available));
        inventory.setQuantity(available - quantity);
        inventoryRepository.save(inventory);
    }
}
