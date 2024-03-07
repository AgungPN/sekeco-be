package com.cashier.system.skecobe.requests;

import com.cashier.system.skecobe.entities.Product;
import jakarta.validation.constraints.Min;
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

    @Min(0)
    private Long price;

    @Min(0)
    private int quantity;

    @Min(0)
    private Long subtotal;
}
