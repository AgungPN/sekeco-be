package com.cashier.system.skecobe.requests.invoiceTour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfitSharingAmountRequest {
    private String name;
    private Long profitSharing;
    private Integer quantity;
    private Long subtotal;
}
