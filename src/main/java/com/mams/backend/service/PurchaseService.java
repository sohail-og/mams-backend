package com.mams.backend.service;

import com.mams.backend.dto.PurchaseRequest;
import com.mams.backend.entity.Purchase;
import com.mams.backend.entity.User;
import com.mams.backend.repository.BaseRepository;
import com.mams.backend.repository.EquipmentTypeRepository;
import com.mams.backend.repository.PurchaseRepository;
import com.mams.backend.repository.UserRepository;
import com.mams.backend.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final InventoryService inventoryService;
    private final SecurityUtils securityUtils;
    private final BaseRepository baseRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;
    private final UserRepository userRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, InventoryService inventoryService, SecurityUtils securityUtils, BaseRepository baseRepository, EquipmentTypeRepository equipmentTypeRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.inventoryService = inventoryService;
        this.securityUtils = securityUtils;
        this.baseRepository = baseRepository;
        this.equipmentTypeRepository = equipmentTypeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Purchase createPurchase(PurchaseRequest request) {
        securityUtils.checkBaseAccess(request.getBaseId());
        
        Purchase purchase = new Purchase();
        purchase.setBase(baseRepository.findById(request.getBaseId()).orElseThrow());
        purchase.setEquipmentType(equipmentTypeRepository.findById(request.getEquipmentTypeId()).orElseThrow());
        purchase.setQuantity(request.getQuantity());
        purchase.setDate(LocalDateTime.now());
        
        User user = userRepository.findById(securityUtils.getCurrentUser().getId()).orElseThrow();
        purchase.setCreatedBy(user);
        
        Purchase saved = purchaseRepository.save(purchase);
        inventoryService.addInventory(request.getBaseId(), request.getEquipmentTypeId(), request.getQuantity());
        
        return saved;
    }

    @Transactional
    public Purchase updatePurchase(Long id, PurchaseRequest request) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Purchase not found"));
        securityUtils.checkBaseAccess(purchase.getBase().getId());
        securityUtils.checkBaseAccess(request.getBaseId());

        // Revert old inventory
        inventoryService.deductInventory(purchase.getBase().getId(), purchase.getEquipmentType().getId(), purchase.getQuantity());

        purchase.setBase(baseRepository.findById(request.getBaseId()).orElseThrow());
        purchase.setEquipmentType(equipmentTypeRepository.findById(request.getEquipmentTypeId()).orElseThrow());
        purchase.setQuantity(request.getQuantity());
        
        // Add new inventory
        inventoryService.addInventory(request.getBaseId(), request.getEquipmentTypeId(), request.getQuantity());

        return purchaseRepository.save(purchase);
    }

    @Transactional
    public void deletePurchase(Long id) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Purchase not found"));
        securityUtils.checkBaseAccess(purchase.getBase().getId());

        inventoryService.deductInventory(purchase.getBase().getId(), purchase.getEquipmentType().getId(), purchase.getQuantity());
        purchaseRepository.delete(purchase);
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }
}
