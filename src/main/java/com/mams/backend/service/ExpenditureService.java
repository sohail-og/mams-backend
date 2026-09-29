package com.mams.backend.service;

import com.mams.backend.dto.ExpenditureRequest;
import com.mams.backend.entity.Expenditure;
import com.mams.backend.entity.User;
import com.mams.backend.repository.BaseRepository;
import com.mams.backend.repository.EquipmentTypeRepository;
import com.mams.backend.repository.ExpenditureRepository;
import com.mams.backend.repository.UserRepository;
import com.mams.backend.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenditureService {

    private final ExpenditureRepository expenditureRepository;
    private final InventoryService inventoryService;
    private final SecurityUtils securityUtils;
    private final BaseRepository baseRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;
    private final UserRepository userRepository;

    public ExpenditureService(ExpenditureRepository expenditureRepository, InventoryService inventoryService, SecurityUtils securityUtils, BaseRepository baseRepository, EquipmentTypeRepository equipmentTypeRepository, UserRepository userRepository) {
        this.expenditureRepository = expenditureRepository;
        this.inventoryService = inventoryService;
        this.securityUtils = securityUtils;
        this.baseRepository = baseRepository;
        this.equipmentTypeRepository = equipmentTypeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Expenditure createExpenditure(ExpenditureRequest request) {
        securityUtils.checkBaseAccess(request.getBaseId());
        
        inventoryService.deductInventory(request.getBaseId(), request.getEquipmentTypeId(), request.getQuantity());

        Expenditure expenditure = new Expenditure();
        expenditure.setBase(baseRepository.findById(request.getBaseId()).orElseThrow());
        expenditure.setEquipmentType(equipmentTypeRepository.findById(request.getEquipmentTypeId()).orElseThrow());
        expenditure.setReason(request.getReason());
        expenditure.setQuantity(request.getQuantity());
        expenditure.setDate(LocalDateTime.now());
        
        User user = userRepository.findById(securityUtils.getCurrentUser().getId()).orElseThrow();
        expenditure.setCreatedBy(user);
        
        return expenditureRepository.save(expenditure);
    }

    public List<Expenditure> getAllExpenditures() {
        return expenditureRepository.findAll();
    }
}
