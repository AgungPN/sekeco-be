package com.cashier.system.skecobe.requests.purchases;

import com.cashier.system.skecobe.requests.purchases.product.ExistingProductRequest;
import com.cashier.system.skecobe.requests.purchases.product.NewProductRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class CreatePurchaseRequest {
    @NotNull
    private Long supplierId;

    @NotNull
    @Positive
    private Long amount;

    @Valid
    private List<NewProductRequest> newProducts;

    @Valid
    private List<ExistingProductRequest> existingProducts;
}
