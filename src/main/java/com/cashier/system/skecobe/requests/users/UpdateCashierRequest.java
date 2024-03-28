package com.cashier.system.skecobe.requests.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UpdateCashierRequest {
    private Long userId;

    @NotBlank
    @Size(max = 200)
    private String username;

    @Size(max = 200)
    private String password;

    @Size(max = 200)
    private String confirmPassword;
}
