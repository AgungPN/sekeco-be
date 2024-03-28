package com.cashier.system.skecobe.responses;

import com.cashier.system.skecobe.entities.OrderDetails;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class OrderDetailsResponse {

    private Long orderDetailId;
    private Long orderId;
    private ProductResponse productId;
    private Long profitSharing;
    private Long price;
    private int quantity;
    private Long subtotal;
    private LocalDate createdAt;
    private LocalDate lastModifiedAt;

    public static OrderDetailsResponse convertToResponse(OrderDetails orderDetails) {
        ProductResponse productResponse = ProductResponse.convertToResponse(orderDetails.getProductId());
        return OrderDetailsResponse.builder()
                .orderDetailId(orderDetails.getOrderDetailId())
                .orderId(orderDetails.getOrderId().getOrderId())
                .productId(productResponse)
                .profitSharing(orderDetails.getProfitSharing())
                .price(orderDetails.getPrice())
                .quantity(orderDetails.getQuantity())
                .subtotal(orderDetails.getSubtotal())
                .createdAt(orderDetails.getCreatedAt())
                .lastModifiedAt(orderDetails.getLastModifiedAt())
                .build();
    }
}
