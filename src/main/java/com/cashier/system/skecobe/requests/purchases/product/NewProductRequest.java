package com.cashier.system.skecobe.requests.purchases.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProductRequest {
    @NotBlank
    @Size(max = 200)
    private String barcode;

    @NotBlank
    @Size(max = 200)
    private String name;

    @Size(max = 200)
    private String brand;

    @NotNull
    @Positive
    private Long priceBuy;

    @NotNull
    @Positive
    private Long priceSell;

    @NotNull
    @Positive
    private Integer quantity;

    @Positive
    private Long profitSharing;

    @NotNull
    private String profitSharedType;
}
