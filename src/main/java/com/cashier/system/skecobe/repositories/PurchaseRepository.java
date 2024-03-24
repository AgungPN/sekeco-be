package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    Page<Purchase> findByCreatedAtBetween(Pageable pageable, LocalDate startDate, LocalDate endDate);
}
