package com.cashier.system.skecobe.responses;

import com.cashier.system.skecobe.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;
    private String username;
    private Role role;
}
