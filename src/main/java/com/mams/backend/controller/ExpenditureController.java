package com.mams.backend.controller;

import com.mams.backend.dto.ExpenditureRequest;
import com.mams.backend.entity.Expenditure;
import com.mams.backend.service.ExpenditureService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenditures")
public class ExpenditureController {
    
    private final ExpenditureService expenditureService;

    public ExpenditureController(ExpenditureService expenditureService) {
        this.expenditureService = expenditureService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BASE_COMMANDER')")
    public ResponseEntity<List<Expenditure>> getAllExpenditures() {
        return ResponseEntity.ok(expenditureService.getAllExpenditures());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BASE_COMMANDER')")
    public ResponseEntity<Expenditure> createExpenditure(@Valid @RequestBody ExpenditureRequest request) {
        return ResponseEntity.ok(expenditureService.createExpenditure(request));
    }
}
