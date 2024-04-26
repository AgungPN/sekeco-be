package com.cashier.system.skecobe.requests.invoiceTour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReprintInvoiceTourRequest {

    private Long invoiceTourId;
    private Long omset;
    private List<ProfitSharingAmountRequest> profitSharingAmounts;
    private List<ProfitSharingPercentageRequest> profitSharingPercentages;
}
