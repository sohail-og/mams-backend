package com.mams.backend.service;

import com.mams.backend.dto.AssignmentRequest;
import com.mams.backend.entity.Assignment;
import com.mams.backend.entity.User;
import com.mams.backend.repository.AssignmentRepository;
import com.mams.backend.repository.BaseRepository;
import com.mams.backend.repository.EquipmentTypeRepository;
import com.mams.backend.repository.UserRepository;
import com.mams.backend.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final InventoryService inventoryService;
    private final SecurityUtils securityUtils;
    private final BaseRepository baseRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;
    private final UserRepository userRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, InventoryService inventoryService, SecurityUtils securityUtils, BaseRepository baseRepository, EquipmentTypeRepository equipmentTypeRepository, UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.inventoryService = inventoryService;
        this.securityUtils = securityUtils;
        this.baseRepository = baseRepository;
        this.equipmentTypeRepository = equipmentTypeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Assignment createAssignment(AssignmentRequest request) {
        securityUtils.checkBaseAccess(request.getBaseId());
        
        inventoryService.deductInventory(request.getBaseId(), request.getEquipmentTypeId(), request.getQuantity());

        Assignment assignment = new Assignment();
        assignment.setBase(baseRepository.findById(request.getBaseId()).orElseThrow());
        assignment.setEquipmentType(equipmentTypeRepository.findById(request.getEquipmentTypeId()).orElseThrow());
        assignment.setPersonnelName(request.getPersonnelName());
        assignment.setQuantity(request.getQuantity());
        assignment.setDate(LocalDateTime.now());
        
        User user = userRepository.findById(securityUtils.getCurrentUser().getId()).orElseThrow();
        assignment.setCreatedBy(user);
        
        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }
}
