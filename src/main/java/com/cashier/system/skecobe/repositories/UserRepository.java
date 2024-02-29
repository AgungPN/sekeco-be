package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.User;
import com.cashier.system.skecobe.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findCashierByRole(Role role);
}
