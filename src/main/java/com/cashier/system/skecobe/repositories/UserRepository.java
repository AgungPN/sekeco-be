package com.cashier.system.skecobe.repositories;

import com.cashier.system.skecobe.entities.Product;
import com.cashier.system.skecobe.entities.User;
import com.cashier.system.skecobe.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String Username);

    Page<User> findByRole(Role role, Pageable pageable);

    Page<User> findByRoleAndUsernameContaining(Role role, String username, Pageable pageable);
}
