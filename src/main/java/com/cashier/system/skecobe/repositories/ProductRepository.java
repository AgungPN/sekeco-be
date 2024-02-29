package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
