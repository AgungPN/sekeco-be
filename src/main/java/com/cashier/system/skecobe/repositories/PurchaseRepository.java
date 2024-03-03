package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
