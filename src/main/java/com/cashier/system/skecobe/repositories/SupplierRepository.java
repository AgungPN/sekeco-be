package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Page<Supplier> findByNameContainingOrPhoneContainingOrderByName(String name, String phone, Pageable pageable);
}
