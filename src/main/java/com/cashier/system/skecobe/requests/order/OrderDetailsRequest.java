package com.cashier.system.skecobe.requests.order;

import com.cashier.system.skecobe.entities.Product;
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

    private Product productId;

    private Long profitSharingAmount;

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