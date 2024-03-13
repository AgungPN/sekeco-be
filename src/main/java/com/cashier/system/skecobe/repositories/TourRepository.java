package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {
    Page<Tour> findByNameContainingOrPhoneContainingOrderByName(String name, String phone, Pageable pageable);
}
