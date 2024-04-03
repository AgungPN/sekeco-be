package com.cashier.system.skecobe.requests.invoiceTour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfitSharingPercentageRequest {
    private Integer profitSharing;
    private Integer quantity;
    private Long subtotal;
    private Double disc;
}
