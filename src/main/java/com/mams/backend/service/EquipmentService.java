package com.mams.backend.service;

import com.mams.backend.dto.DashboardMetricsResponse;
import com.mams.backend.dto.EquipmentDto;
import com.mams.backend.dto.BaseEquipmentDto;
import com.mams.backend.entity.EquipmentType;
import com.mams.backend.entity.Base;
import com.mams.backend.repository.EquipmentTypeRepository;
import com.mams.backend.repository.BaseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    private final EquipmentTypeRepository equipmentTypeRepository;
    private final BaseRepository baseRepository;
    private final DashboardService dashboardService;

    public EquipmentService(EquipmentTypeRepository equipmentTypeRepository, BaseRepository baseRepository, DashboardService dashboardService) {
        this.equipmentTypeRepository = equipmentTypeRepository;
        this.baseRepository = baseRepository;
        this.dashboardService = dashboardService;
    }

    public List<EquipmentDto> getAllEquipmentInventory() {
        List<EquipmentType> equipmentTypes = equipmentTypeRepository.findAll();
        
        return equipmentTypes.stream().map(type -> {
            DashboardMetricsResponse metrics = dashboardService.getMetrics(null, type.getId(), null, null);
            int totalQuantity = metrics.getOpeningBalance() + metrics.getNetMovement();
            int availableQuantity = metrics.getClosingBalance();
            String status = availableQuantity > 0 ? "Available" : "Out of Stock";
            
            return new EquipmentDto(
                type.getId(),
                type.getName(),
                type.getCategory(),
                totalQuantity,
                availableQuantity,
                status
            );
        }).collect(Collectors.toList());
    }

    public List<BaseEquipmentDto> getBaseWiseEquipmentInventory(Long baseId, Long equipmentTypeId) {
        List<Base> bases = baseRepository.findAll();
        List<EquipmentType> equipmentTypes = equipmentTypeRepository.findAll();

        if (baseId != null) {
            bases = bases.stream().filter(b -> b.getId().equals(baseId)).collect(Collectors.toList());
        }
        if (equipmentTypeId != null) {
            equipmentTypes = equipmentTypes.stream().filter(e -> e.getId().equals(equipmentTypeId)).collect(Collectors.toList());
        }

        List<BaseEquipmentDto> result = new ArrayList<>();

        for (Base base : bases) {
            for (EquipmentType type : equipmentTypes) {
                DashboardMetricsResponse metrics = dashboardService.getMetrics(base.getId(), type.getId(), null, null);
                
                int totalQuantity = metrics.getOpeningBalance() + metrics.getNetMovement();
                int availableQuantity = metrics.getClosingBalance();
                
                // Only include if there is some activity or existing stock, otherwise the table might be too long with 0s.
                // Or maybe include all? The requirement says "Base-wise Inventory must reflect actual inventory..."
                // I will just include everything so it matches exactly the requirements.
                // Actually, skipping 0 total helps UI. Let's include everything just in case, or maybe only if total > 0.
                if (totalQuantity > 0 || availableQuantity > 0) {
                    String status = availableQuantity > 0 ? "Available" : "Out of Stock";
                    result.add(new BaseEquipmentDto(
                        base.getId(),
                        base.getName(),
                        type.getId(),
                        type.getName(),
                        totalQuantity,
                        availableQuantity,
                        status
                    ));
                }
            }
        }
        return result;
    }
}
