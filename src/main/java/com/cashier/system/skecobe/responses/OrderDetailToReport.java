package com.cashier.system.skecobe.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailToReport {
    private String name;
    private Long price;
    private int quantity;
    private Long subtotal;

    public  static OrderDetailToReport convertToDataReport(OrderDetailsResponse odr){
        return OrderDetailToReport.builder()
                .name(odr.getProductId().getName())
                .price(odr.getPrice())
                .quantity(odr.getQuantity())
                .subtotal(odr.getSubtotal())
                .build();
    }
}