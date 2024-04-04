package com.cashier.system.skecobe.requests.order;

import com.cashier.system.skecobe.entities.Product;
import com.cashier.system.skecobe.enums.ProfitShared;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class OrderDetailsRequest {
    private Long productId;

    private Long profitSharing;private String profitSharedType;

    @NotNull
    @Positive
    private Long price;

    @NotNull
    @Positive
    private int quantity;

    @NotNull
    @Positive
    private Long subtotal;
}
