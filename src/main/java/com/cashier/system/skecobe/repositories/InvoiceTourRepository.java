package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.InvoiceTour;
import com.cashier.system.skecobe.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceTourRepository extends JpaRepository<InvoiceTour, Long> {
    List<InvoiceTour> findByStatus(Status status);
}
