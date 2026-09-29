package com.mams.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AssignmentRequest {
    @NotNull
    private Long baseId;

    @NotNull
    private Long equipmentTypeId;

    @NotBlank
    private String personnelName;

    @NotNull
    @Min(1)
    private Integer quantity;

    public AssignmentRequest() {}

    public Long getBaseId() { return baseId; }
    public void setBaseId(Long baseId) { this.baseId = baseId; }

    public Long getEquipmentTypeId() { return equipmentTypeId; }
    public void setEquipmentTypeId(Long equipmentTypeId) { this.equipmentTypeId = equipmentTypeId; }

    public String getPersonnelName() { return personnelName; }
    public void setPersonnelName(String personnelName) { this.personnelName = personnelName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
