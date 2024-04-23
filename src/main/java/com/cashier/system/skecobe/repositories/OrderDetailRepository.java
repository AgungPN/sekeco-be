package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);
}
