package com.mams.backend.service;

import com.mams.backend.dto.OpeningBalanceRequest;
import com.mams.backend.entity.Base;
import com.mams.backend.entity.EquipmentType;
import com.mams.backend.entity.OpeningBalance;
import com.mams.backend.repository.BaseRepository;
import com.mams.backend.repository.EquipmentTypeRepository;
import com.mams.backend.repository.OpeningBalanceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OpeningBalanceService {

    private final OpeningBalanceRepository openingBalanceRepository;
    private final BaseRepository baseRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;

    public OpeningBalanceService(OpeningBalanceRepository openingBalanceRepository,
                                 BaseRepository baseRepository,
                                 EquipmentTypeRepository equipmentTypeRepository) {
        this.openingBalanceRepository = openingBalanceRepository;
        this.baseRepository = baseRepository;
        this.equipmentTypeRepository = equipmentTypeRepository;
    }

    @org.springframework.transaction.annotation.Transactional
    public OpeningBalance setOpeningBalance(OpeningBalanceRequest request) {
        Base base = baseRepository.findById(request.getBaseId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Base ID"));
        
        EquipmentType type = equipmentTypeRepository.findById(request.getEquipmentTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Equipment Type ID"));

        OpeningBalance.OpeningBalanceId id = new OpeningBalance.OpeningBalanceId(request.getBaseId(), request.getEquipmentTypeId());
        
        OpeningBalance openingBalance = openingBalanceRepository.findById(id)
                .orElseGet(() -> {
                    OpeningBalance ob = new OpeningBalance(base, type, 0);
                    ob.setId(id);
                    return ob;
                });
        
        openingBalance.setQuantity(request.getQuantity());
        return openingBalanceRepository.save(openingBalance);
    }
}
