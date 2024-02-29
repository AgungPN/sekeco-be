package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.User;
import com.cashier.system.skecobe.enums.Role;
import com.cashier.system.skecobe.handlers.exceptions.NotFoundException;
import com.cashier.system.skecobe.repositories.UserRepository;
import com.cashier.system.skecobe.requests.users.CreateCashierRequest;
import com.cashier.system.skecobe.requests.users.UpdateCashierRequest;
import com.cashier.system.skecobe.responses.UserResponse;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private ValidationService validationService;


    public List<UserResponse> getListCashier() {
        var users = userRepository.findCashierByRole(Role.CASHIER);
        return users.stream().map(UserResponse::convertToResponse).toList();
    }

    public UserResponse findById(Long userId) {
        return userRepository.findById(userId)
                .map(UserResponse::convertToResponse)
                .orElseThrow(() -> new NotFoundException("User"));
    }

    public UserResponse save(CreateCashierRequest createUserRequest) throws BadRequestException {
        validationService.validate(createUserRequest);

        if (createUserRequest.getPassword().length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long");
        }

        if (!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            throw new BadRequestException("Password and Confirm Password must be the same");
        }

        User user = User.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword()) // TODO: hash password with bcrypt
                .role(Role.CASHIER)
                .build();

        userRepository.save(user);

        return UserResponse.convertToResponse(user);
    }

    public UserResponse update(Long id, UpdateCashierRequest userRequest) throws BadRequestException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User"));

        if (userRequest.getPassword().length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long");
        }

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new BadRequestException("Password and Confirm Password must be the same");
        }

        validationService.validate(userRequest);

        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword()); // TODO: hash password with bcrypt
        userRepository.save(user);

        return UserResponse.convertToResponse(user);
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
