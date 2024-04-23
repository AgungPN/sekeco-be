package com.cashier.system.skecobe.requests.invoiceTour;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceTourRequestToReport {
    private Long invoiceTourId;
    private Long tourId;
    private String tourName;
    private Integer unitBus;
    private Integer employee;
    private Long omset;
    private Double employeeAmount;
    private Long totalProfitSharing;
    private List<ProfitSharingAmountRequest> profitSharingAmounts;
    private List<ProfitSharingPercentageRequest> profitSharingPercentages;
}
