package com.mams.backend.repository;

import com.mams.backend.entity.OpeningBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpeningBalanceRepository extends JpaRepository<OpeningBalance, OpeningBalance.OpeningBalanceId> {
    List<OpeningBalance> findByBaseId(Long baseId);
    Optional<OpeningBalance> findByBaseIdAndEquipmentTypeId(Long baseId, Long equipmentTypeId);
}
