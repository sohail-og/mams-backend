package com.mams.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TransferRequest {
    @NotNull
    private Long fromBaseId;

    @NotNull
    private Long toBaseId;

    @NotNull
    private Long equipmentTypeId;

    @NotNull
    @Min(1)
    private Integer quantity;

    public TransferRequest() {}

    public Long getFromBaseId() { return fromBaseId; }
    public void setFromBaseId(Long fromBaseId) { this.fromBaseId = fromBaseId; }

    public Long getToBaseId() { return toBaseId; }
    public void setToBaseId(Long toBaseId) { this.toBaseId = toBaseId; }

    public Long getEquipmentTypeId() { return equipmentTypeId; }
    public void setEquipmentTypeId(Long equipmentTypeId) { this.equipmentTypeId = equipmentTypeId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
