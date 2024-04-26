package com.cashier.system.skecobe.model;

import com.cashier.system.skecobe.enums.ProfitShared;

import java.time.LocalDate;

public class OrderDetailSum {
    private Long productId;
    private String barcode;
    private String name;
    private String brand;
    private int count = 0;
    private Long profitSharing;
    private ProfitShared profitSharedType;
    private Long price;
    private Integer stock;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
