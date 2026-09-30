package com.mams.backend.dto;

public class EquipmentDto {
    private Long id;
    private String name;
    private String category;
    private int totalQuantity;
    private int availableQuantity;
    private String status;

    public EquipmentDto(Long id, String name, String category, int totalQuantity, int availableQuantity, String status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getTotalQuantity() { return totalQuantity; }
    public int getAvailableQuantity() { return availableQuantity; }
    public String getStatus() { return status; }
}
