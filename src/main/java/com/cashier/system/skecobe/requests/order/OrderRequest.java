package com.cashier.system.skecobe.requests;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class OrderRequest {

    private Long cashierId;

    private Long invoiceTourId;

    @Min(0)
    private int totalItems;

    @Min(0)
    private Long totalPrice;

    @Min(0)
    private Long amount;

    @Min(0)
    private Long refund;

    List<OrderDetailsRequest> orderDetailsRequests;
}
