package com.cashier.system.skecobe.requests.purchases.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExistingProductRequest {
    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Long priceBuy;

    @NotNull
    @Positive
    private Long priceSell;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    private Long profitSharingAmount;
}
