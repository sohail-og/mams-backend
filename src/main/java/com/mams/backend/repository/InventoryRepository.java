package com.mams.backend.repository;

import com.mams.backend.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Inventory.InventoryId> {
    List<Inventory> findByBaseId(Long baseId);
}
