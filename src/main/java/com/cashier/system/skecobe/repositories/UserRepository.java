package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.User;
import com.cashier.system.skecobe.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.role = :role")
    Page<User> findCashierByRole(Role role, Pageable pageable);

    Optional<User> findByUsername(String Username);
}
