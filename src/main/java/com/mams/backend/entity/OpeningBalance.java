package com.mams.backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "opening_balances")
public class OpeningBalance {
    
    @EmbeddedId
    private OpeningBalanceId id = new OpeningBalanceId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("baseId")
    @JoinColumn(name = "base_id", nullable = false)
    private Base base;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipmentTypeId")
    @JoinColumn(name = "equipment_type_id", nullable = false)
    private EquipmentType equipmentType;

    @Column(nullable = false)
    private Integer quantity;

    public OpeningBalance() {}

    public OpeningBalance(Base base, EquipmentType equipmentType, Integer quantity) {
        this.base = base;
        this.equipmentType = equipmentType;
        this.quantity = quantity;
    }

    public OpeningBalanceId getId() { return id; }
    public void setId(OpeningBalanceId id) { this.id = id; }

    public Base getBase() { return base; }
    public void setBase(Base base) { this.base = base; }

    public EquipmentType getEquipmentType() { return equipmentType; }
    public void setEquipmentType(EquipmentType equipmentType) { this.equipmentType = equipmentType; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    @Embeddable
    public static class OpeningBalanceId implements Serializable {
        private Long baseId;
        private Long equipmentTypeId;

        public OpeningBalanceId() {}
        public OpeningBalanceId(Long baseId, Long equipmentTypeId) {
            this.baseId = baseId;
            this.equipmentTypeId = equipmentTypeId;
        }

        public Long getBaseId() { return baseId; }
        public void setBaseId(Long baseId) { this.baseId = baseId; }

        public Long getEquipmentTypeId() { return equipmentTypeId; }
        public void setEquipmentTypeId(Long equipmentTypeId) { this.equipmentTypeId = equipmentTypeId; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OpeningBalanceId that = (OpeningBalanceId) o;
            return Objects.equals(baseId, that.baseId) &&
                   Objects.equals(equipmentTypeId, that.equipmentTypeId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(baseId, equipmentTypeId);
        }
    }
}
