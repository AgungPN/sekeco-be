package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.InvoiceTour;
import com.cashier.system.skecobe.entities.Tour;
import com.cashier.system.skecobe.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceTourRepository extends JpaRepository<InvoiceTour, Long> {
    List<InvoiceTour> findByStatus(Status status);
    Page<InvoiceTour> findByStatus(Pageable pageable, Status status);
    InvoiceTour findByStatusAndTourId(Status status, Tour tourId);
    Page<InvoiceTour> findByStatusAndCreatedAtBetween(Status status, Pageable pageable, LocalDate startDate, LocalDate endDate);
}
