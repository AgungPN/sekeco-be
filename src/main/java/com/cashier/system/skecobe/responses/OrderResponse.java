package com.cashier.system.skecobe.responses;

import com.cashier.system.skecobe.entities.InvoiceTour;
import com.cashier.system.skecobe.entities.Order;
import com.cashier.system.skecobe.entities.OrderDetails;
import com.cashier.system.skecobe.entities.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class OrderResponse {

    private Long orderId;
    private UserResponse userId;
    private InvoiceTourResponse invoiceTourId;
    private int totalItems;
    private Long totalPrice;
    private Long amount;
    private Long refund;
    private List<OrderDetailsResponse> orderDetails;
    private LocalDate createdAt;
    private LocalDate lastModifiedAt;

    public static OrderResponse convertToResponse(Order order) {
        List<OrderDetailsResponse> detailsResponse = order.getOrderDetails().stream()
                .map(OrderDetailsResponse::convertToResponse)
                .toList();
        UserResponse response = UserResponse.convertToResponse(order.getUserId());
        InvoiceTourResponse invoiceTourResponse = InvoiceTourResponse.convertToResponse(order.getInvoiceTourId());
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(response)
                .invoiceTourId(invoiceTourResponse)
                .totalItems(order.getTotalItems())
                .totalPrice(order.getTotalPrice())
                .amount(order.getAmount())
                .refund(order.getRefund())
                .orderDetails(detailsResponse)
                .createdAt(order.getCreatedAt())
                .lastModifiedAt(order.getLastModifiedAt())
                .build();
    }
}
