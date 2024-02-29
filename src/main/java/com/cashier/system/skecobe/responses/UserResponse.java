package com.cashier.system.skecobe.responses;

import com.cashier.system.skecobe.entities.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserResponse {

    private Long userId;
    private String username;
    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public static UserResponse convertToResponse(User cashier) {
        return UserResponse.builder()
                .userId(cashier.getUserId())
                .username(cashier.getUsername())
                .password(cashier.getPassword())
                .createdAt(cashier.getCreatedAt())
                .updatedAt(cashier.getUpdatedAt())
                .build();
    }
}
