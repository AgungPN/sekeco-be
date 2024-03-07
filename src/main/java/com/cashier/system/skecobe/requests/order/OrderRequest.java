package com.cashier.system.skecobe.requests.order;

import com.cashier.system.skecobe.entities.InvoiceTour;
import com.cashier.system.skecobe.entities.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    private User userId;

    private InvoiceTour invoiceTourId;

    @NotNull
    @Positive
    private int totalItems;

    @NotNull
    @Positive
    private Long totalPrice;

    @NotNull
    @Positive
    private Long amount;

    @NotNull
    private Long refund;

    List<OrderDetailsRequest> orderDetailsRequests;
}
