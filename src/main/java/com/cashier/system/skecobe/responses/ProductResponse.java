package com.cashier.system.skecobe.responses;


import com.cashier.system.skecobe.entities.Product;
import com.cashier.system.skecobe.enums.ProfitShared;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
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

    public static ProductResponse convertToResponse(Product product) {
        if(product == null){
            return null;
        }
        return ProductResponse.builder()
                .productId(product.getProductId())
                .barcode(product.getBarcode())
                .name(product.getName())
                .brand(product.getBrand())
                .profitSharing(product.getProfitSharing())
                .profitSharedType(product.getProfitSharedType())
                .price(product.getPrice())
                .stock(product.getStock())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getLastModifiedAt())
                .build();
    }
}
