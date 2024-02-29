package com.cashier.system.skecobe.responses;


import com.cashier.system.skecobe.entities.Supplier;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SupplierResponse {

    private Long supplierId;
    private String name;
    private String address;
    private String phone;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public static SupplierResponse convertToResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .supplierId(supplier.getSupplierId())
                .name(supplier.getName())
                .address(supplier.getAddress())
                .phone(supplier.getPhone())
                .createdAt(supplier.getCreatedAt())
                .updatedAt(supplier.getUpdatedAt())
                .build();
    }
}
