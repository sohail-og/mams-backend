package com.mams.backend.service;

import com.mams.backend.dto.TransferRequest;
import com.mams.backend.entity.Transfer;
import com.mams.backend.entity.User;
import com.mams.backend.repository.BaseRepository;
import com.mams.backend.repository.EquipmentTypeRepository;
import com.mams.backend.repository.TransferRepository;
import com.mams.backend.repository.UserRepository;
import com.mams.backend.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final InventoryService inventoryService;
    private final SecurityUtils securityUtils;
    private final BaseRepository baseRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;
    private final UserRepository userRepository;

    public TransferService(TransferRepository transferRepository, InventoryService inventoryService, SecurityUtils securityUtils, BaseRepository baseRepository, EquipmentTypeRepository equipmentTypeRepository, UserRepository userRepository) {
        this.transferRepository = transferRepository;
        this.inventoryService = inventoryService;
        this.securityUtils = securityUtils;
        this.baseRepository = baseRepository;
        this.equipmentTypeRepository = equipmentTypeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Transfer createTransfer(TransferRequest request) {
        securityUtils.checkBaseAccess(request.getFromBaseId());

        if (request.getFromBaseId().equals(request.getToBaseId())) {
            throw new IllegalArgumentException("Source and destination base cannot be the same");
        }
        
        inventoryService.deductInventory(request.getFromBaseId(), request.getEquipmentTypeId(), request.getQuantity());
        inventoryService.addInventory(request.getToBaseId(), request.getEquipmentTypeId(), request.getQuantity());

        Transfer transfer = new Transfer();
        transfer.setFromBase(baseRepository.findById(request.getFromBaseId()).orElseThrow());
        transfer.setToBase(baseRepository.findById(request.getToBaseId()).orElseThrow());
        transfer.setEquipmentType(equipmentTypeRepository.findById(request.getEquipmentTypeId()).orElseThrow());
        transfer.setQuantity(request.getQuantity());
        transfer.setDate(LocalDateTime.now());
        
        User user = userRepository.findById(securityUtils.getCurrentUser().getId()).orElseThrow();
        transfer.setCreatedBy(user);
        
        return transferRepository.save(transfer);
    }

    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    public Transfer getTransferById(Long id) {
        Transfer transfer = transferRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transfer not found"));
        securityUtils.checkBaseAccess(transfer.getFromBase().getId()); // Just checking source base access is simple enough, or allow if either
        return transfer;
    }
}
