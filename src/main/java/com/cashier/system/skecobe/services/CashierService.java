package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.User;
import com.cashier.system.skecobe.enums.Role;
import com.cashier.system.skecobe.handlers.exceptions.NotFoundException;
import com.cashier.system.skecobe.repositories.UserRepository;
import com.cashier.system.skecobe.requests.users.CreateCashierRequest;
import com.cashier.system.skecobe.requests.users.UpdateCashierRequest;
import com.cashier.system.skecobe.responses.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CashierService {

    private UserRepository userRepository;
    private ValidationService validationService;

    public Page<UserResponse> getListCashier(Pageable pageable) {
        var users = userRepository.findByRole(Role.ROLE_CASHIER, pageable);
        return users.map(UserResponse::convertToResponse);
    }

    public Page<UserResponse> getListCashier(String search, Pageable pageable) {
        var users = userRepository.findByRoleAndUsernameContaining(Role.ROLE_CASHIER, search, pageable);
        return users.map(UserResponse::convertToResponse);
    }

    public UserResponse findById(Long userId) {
        return userRepository.findById(userId)
                .map(UserResponse::convertToResponse)
                .orElseThrow(() -> new NotFoundException("Cashier"));
    }

    public UserResponse save(CreateCashierRequest createUserRequest) {
        validationService.validate(createUserRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (createUserRequest.getPassword().length() < 8) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Password must be at least 8 characters long");
        }

        if (!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Password and Confirm Password must be the same");
        }

        User user = User.builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .role(Role.ROLE_CASHIER) // This registration is only for cashier
                .build();

        userRepository.save(user);

        return UserResponse.convertToResponse(user);
    }

    public UserResponse update(Long id, UpdateCashierRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User"));

        if (userRequest.getPassword().length() < 8) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Password must be at least 8 characters long");
        }

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Password and Confirm Password must be the same");
        }

        validationService.validate(userRequest);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);

        return UserResponse.convertToResponse(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
