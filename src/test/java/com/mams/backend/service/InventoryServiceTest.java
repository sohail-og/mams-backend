package com.mams.backend.service;

import com.mams.backend.entity.Base;
import com.mams.backend.entity.EquipmentType;
import com.mams.backend.entity.Inventory;
import com.mams.backend.exception.InsufficientStockException;
import com.mams.backend.repository.BaseRepository;
import com.mams.backend.repository.EquipmentTypeRepository;
import com.mams.backend.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    private InventoryRepository inventoryRepository;
    private BaseRepository baseRepository;
    private EquipmentTypeRepository equipmentTypeRepository;
    private DashboardService dashboardService;
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryRepository = mock(InventoryRepository.class);
        baseRepository = mock(BaseRepository.class);
        equipmentTypeRepository = mock(EquipmentTypeRepository.class);
        dashboardService = mock(DashboardService.class);
        inventoryService = new InventoryService(inventoryRepository, baseRepository, equipmentTypeRepository, dashboardService);
    }

    @Test
    void deductInventory_Success() {
        Base base = new Base();
        base.setId(1L);
        base.setName("Test Base");
        EquipmentType type = new EquipmentType();
        type.setId(1L);
        type.setName("Test Equip");
        
        when(baseRepository.findById(any())).thenReturn(Optional.of(base));
        when(equipmentTypeRepository.findById(any())).thenReturn(Optional.of(type));
        
        Inventory inventory = new Inventory(base, type, 10);
        when(inventoryRepository.findById(any())).thenReturn(Optional.of(inventory));
        
        com.mams.backend.dto.DashboardMetricsResponse dummyMetrics = new com.mams.backend.dto.DashboardMetricsResponse();
        dummyMetrics.setClosingBalance(15);
        when(dashboardService.getMetrics(anyLong(), anyLong(), any(), any())).thenReturn(dummyMetrics);

        inventoryService.deductInventory(1L, 1L, 5);

        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void deductInventory_InsufficientStock() {
        Base base = new Base();
        base.setId(1L);
        base.setName("Test Base");
        EquipmentType type = new EquipmentType();
        type.setId(1L);
        type.setName("Test Equip");
        
        when(baseRepository.findById(any())).thenReturn(Optional.of(base));
        when(equipmentTypeRepository.findById(any())).thenReturn(Optional.of(type));
        
        Inventory inventory = new Inventory(base, type, 5);
        when(inventoryRepository.findById(any())).thenReturn(Optional.of(inventory));

        com.mams.backend.dto.DashboardMetricsResponse dummyMetrics = new com.mams.backend.dto.DashboardMetricsResponse();
        dummyMetrics.setClosingBalance(5);
        when(dashboardService.getMetrics(anyLong(), anyLong(), any(), any())).thenReturn(dummyMetrics);

        assertThrows(InsufficientStockException.class, () -> {
            inventoryService.deductInventory(1L, 1L, 10);
        });
    }
}
