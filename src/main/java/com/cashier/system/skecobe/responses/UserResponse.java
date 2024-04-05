package com.cashier.system.skecobe.responses;

import com.cashier.system.skecobe.entities.User;
import com.cashier.system.skecobe.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserResponse {

    private Long userId;
    private String username;
    private Role role;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public static UserResponse convertToResponse(User cashier) {
        if(cashier == null){
            return null;
        }
        return UserResponse.builder()
                .userId(cashier.getUserId())
                .username(cashier.getUsername())
                .role(cashier.getRole())
                .createdAt(cashier.getCreatedAt())
                .updatedAt(cashier.getUpdatedAt())
                .build();
    }
}
