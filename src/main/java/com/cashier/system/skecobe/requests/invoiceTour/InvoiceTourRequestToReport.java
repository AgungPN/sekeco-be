package com.cashier.system.skecobe.requests.invoiceTour;

import com.cashier.system.skecobe.entities.InvoiceTour;
import com.cashier.system.skecobe.enums.ProfitShared;
import com.cashier.system.skecobe.responses.OrderDetailsResponse;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceTourRequestToReport {
    private Long invoiceTourId;
    private Long tourId;
    private Long userId = 1L;
    private String tourName;
    private Integer unitBus;
    private Integer employee;
    private Long omset;
    private Double employeeAmount;
    private Long totalProfitSharing;
    private List<ProfitSharingAmountRequest> profitSharingAmounts;
    private List<ProfitSharingPercentageRequest> profitSharingPercentages;

}
