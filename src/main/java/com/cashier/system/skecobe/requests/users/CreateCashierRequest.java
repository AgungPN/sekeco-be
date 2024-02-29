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
public class CreateCashierRequest {
    @NotBlank
    @Size(max = 200)
    private String username;

    @NotBlank
    @Size(max = 200)
    private String password;

    @NotBlank
    @Size(max = 200)
    private String confirmPassword;
}
