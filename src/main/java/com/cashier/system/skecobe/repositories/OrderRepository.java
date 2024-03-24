package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.InvoiceTour;
import com.cashier.system.skecobe.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order , Long> {
    List<Order> findByInvoiceTourId(InvoiceTour invoiceTourId);
    Page<Order> findByCreatedAtBetween(Pageable pageable, LocalDate startDate, LocalDate endDate);
}
