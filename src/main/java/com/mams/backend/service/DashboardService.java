package com.mams.backend.service;

import com.mams.backend.dto.DashboardMetricsResponse;
import com.mams.backend.entity.Assignment;
import com.mams.backend.entity.Expenditure;
import com.mams.backend.entity.Purchase;
import com.mams.backend.entity.Transfer;
import com.mams.backend.repository.AssignmentRepository;
import com.mams.backend.repository.ExpenditureRepository;
import com.mams.backend.repository.PurchaseRepository;
import com.mams.backend.repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    
    private final PurchaseRepository purchaseRepository;
    private final TransferRepository transferRepository;
    private final AssignmentRepository assignmentRepository;
    private final ExpenditureRepository expenditureRepository;

    public DashboardService(PurchaseRepository purchaseRepository, TransferRepository transferRepository, AssignmentRepository assignmentRepository, ExpenditureRepository expenditureRepository) {
        this.purchaseRepository = purchaseRepository;
        this.transferRepository = transferRepository;
        this.assignmentRepository = assignmentRepository;
        this.expenditureRepository = expenditureRepository;
    }

    public DashboardMetricsResponse getMetrics(Long baseId, Long equipmentTypeId, String startDate, String endDate) {
        List<Purchase> purchases = purchaseRepository.findAll();
        List<Transfer> transfers = transferRepository.findAll();
        List<Assignment> assignments = assignmentRepository.findAll();
        List<Expenditure> expenditures = expenditureRepository.findAll();

        if (baseId != null) {
            purchases = purchases.stream().filter(p -> p.getBase().getId().equals(baseId)).collect(Collectors.toList());
            transfers = transfers.stream().filter(t -> t.getFromBase().getId().equals(baseId) || t.getToBase().getId().equals(baseId)).collect(Collectors.toList());
            assignments = assignments.stream().filter(a -> a.getBase().getId().equals(baseId)).collect(Collectors.toList());
            expenditures = expenditures.stream().filter(e -> e.getBase().getId().equals(baseId)).collect(Collectors.toList());
        }

        if (equipmentTypeId != null) {
            purchases = purchases.stream().filter(p -> p.getEquipmentType().getId().equals(equipmentTypeId)).collect(Collectors.toList());
            transfers = transfers.stream().filter(t -> t.getEquipmentType().getId().equals(equipmentTypeId)).collect(Collectors.toList());
            assignments = assignments.stream().filter(a -> a.getEquipmentType().getId().equals(equipmentTypeId)).collect(Collectors.toList());
            expenditures = expenditures.stream().filter(e -> e.getEquipmentType().getId().equals(equipmentTypeId)).collect(Collectors.toList());
        }

        if (startDate != null && !startDate.isEmpty()) {
            java.time.LocalDate start = java.time.LocalDate.parse(startDate);
            purchases = purchases.stream().filter(p -> !p.getDate().toLocalDate().isBefore(start)).collect(Collectors.toList());
            transfers = transfers.stream().filter(t -> !t.getDate().toLocalDate().isBefore(start)).collect(Collectors.toList());
            assignments = assignments.stream().filter(a -> !a.getDate().toLocalDate().isBefore(start)).collect(Collectors.toList());
            expenditures = expenditures.stream().filter(e -> !e.getDate().toLocalDate().isBefore(start)).collect(Collectors.toList());
        }

        if (endDate != null && !endDate.isEmpty()) {
            java.time.LocalDate end = java.time.LocalDate.parse(endDate);
            purchases = purchases.stream().filter(p -> !p.getDate().toLocalDate().isAfter(end)).collect(Collectors.toList());
            transfers = transfers.stream().filter(t -> !t.getDate().toLocalDate().isAfter(end)).collect(Collectors.toList());
            assignments = assignments.stream().filter(a -> !a.getDate().toLocalDate().isAfter(end)).collect(Collectors.toList());
            expenditures = expenditures.stream().filter(e -> !e.getDate().toLocalDate().isAfter(end)).collect(Collectors.toList());
        }

        int totalPurchases = purchases.stream().mapToInt(Purchase::getQuantity).sum();
        
        int transfersIn = 0;
        int transfersOut = 0;
        
        for (Transfer t : transfers) {
            if (baseId == null) {
                // If no base filter, transfer out and in cancel out
            } else {
                if (t.getToBase().getId().equals(baseId)) transfersIn += t.getQuantity();
                if (t.getFromBase().getId().equals(baseId)) transfersOut += t.getQuantity();
            }
        }

        int netMovement = totalPurchases + transfersIn - transfersOut;
        int totalExpended = expenditures.stream().mapToInt(Expenditure::getQuantity).sum();
        int totalAssigned = assignments.stream().mapToInt(Assignment::getQuantity).sum();
        
        int openingBalance = 100;
        
        int closingBalance = openingBalance + netMovement - totalExpended - totalAssigned;

        return new DashboardMetricsResponse(openingBalance, closingBalance, netMovement, totalAssigned, totalExpended);
    }
}
