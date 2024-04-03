package com.cashier.system.skecobe.responses;

import com.cashier.system.skecobe.entities.Purchase;
import com.cashier.system.skecobe.entities.Supplier;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Builder
public class PurchaseResponse {
    private Long purchaseId;
    private SupplierResponse supplier;
    private Integer totalItems;
    private Long totalPrice;
    private Integer discount = 0;
    private Long amount;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public  static PurchaseResponse convertToResponse(Purchase purchase){
        return PurchaseResponse.builder()
                .purchaseId(purchase.getPurchaseId())
                .supplier(SupplierResponse.convertToResponse(purchase.getSupplier()))
                .totalItems(purchase.getTotalItems())
                .totalPrice(purchase.getTotalPrice())
                .discount(purchase.getDiscount())
                .amount(purchase.getAmount())
                .createdAt(purchase.getCreatedAt())
                .updatedAt(purchase.getUpdatedAt())
                .build();
    }
}
