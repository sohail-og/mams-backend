package com.mams.backend.controller;

import com.mams.backend.dto.OpeningBalanceRequest;
import com.mams.backend.entity.OpeningBalance;
import com.mams.backend.service.OpeningBalanceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/opening-balances")
public class OpeningBalanceController {

    private final OpeningBalanceService openingBalanceService;

    public OpeningBalanceController(OpeningBalanceService openingBalanceService) {
        this.openingBalanceService = openingBalanceService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BASE_COMMANDER', 'LOGISTICS_OFFICER')")
    public ResponseEntity<OpeningBalance> setOpeningBalance(@Valid @RequestBody OpeningBalanceRequest request) {
        return ResponseEntity.ok(openingBalanceService.setOpeningBalance(request));
    }
}
