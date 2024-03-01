package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order , Long> {
}
