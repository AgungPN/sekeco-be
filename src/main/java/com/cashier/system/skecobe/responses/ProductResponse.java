package com.cashier.system.skecobe.responses;


import com.cashier.system.skecobe.entities.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ProductResponse {
    private Long productId;
    private String barcode;
    private String name;
    private String brand;
    private Long profitSharingAmount;
    private Long price;
    private Integer stock;
    private LocalDate createdAt;
    private LocalDate lastModifiedAt;

    public static ProductResponse convertToResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .barcode(product.getBarcode())
                .name(product.getName())
                .brand(product.getBrand())
                .profitSharingAmount(product.getProfitSharingAmount())
                .price(product.getPrice())
                .stock(product.getStock())
                .createdAt(product.getCreatedAt())
                .lastModifiedAt(product.getLastModifiedAt())
                .build();
    }
}
