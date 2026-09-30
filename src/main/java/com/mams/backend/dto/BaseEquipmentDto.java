package com.mams.backend.dto;

public class BaseEquipmentDto {
    private Long baseId;
    private String baseName;
    private Long equipmentId;
    private String equipmentName;
    private int totalQuantity;
    private int availableQuantity;
    private String status;

    public BaseEquipmentDto(Long baseId, String baseName, Long equipmentId, String equipmentName, int totalQuantity, int availableQuantity, String status) {
        this.baseId = baseId;
        this.baseName = baseName;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.status = status;
    }

    public Long getBaseId() { return baseId; }
    public String getBaseName() { return baseName; }
    public Long getEquipmentId() { return equipmentId; }
    public String getEquipmentName() { return equipmentName; }
    public int getTotalQuantity() { return totalQuantity; }
    public int getAvailableQuantity() { return availableQuantity; }
    public String getStatus() { return status; }
}
