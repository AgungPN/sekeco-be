package com.cashier.system.skecobe.requests.invoiceTour;

import com.cashier.system.skecobe.enums.ProfitShared;
import com.cashier.system.skecobe.responses.OrderDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfitSharingAmountRequest {
    private String name;
    private Long profitSharing;
    private Integer quantity;
    private Long subtotal;

}
