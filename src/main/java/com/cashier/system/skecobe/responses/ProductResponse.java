package com.cashier.system.skecobe.responses;


import com.cashier.system.skecobe.entities.Product;
import com.cashier.system.skecobe.helpers.Storage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Long price;

    private Long profitSharingPercentage;
    private Long profitSharingAmount;
    private Integer stock;
    private String imageUrl;
    private LocalDate createdAt;
    private LocalDate lastModifiedAt;

    public static ProductResponse convertToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .profitSharingPercentage(product.getProfitSharingPercentage())
                .profitSharingAmount(product.getProfitSharingAmount())
                .stock(product.getStock())
                .imageUrl(product.getImage())
                .createdAt(product.getCreatedAt())
                .lastModifiedAt(product.getLastModifiedAt())
                .imageUrl(Storage.getFileUrl(product.getImage(), "images/products"))
                .build();
    }
}
