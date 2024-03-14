package com.cashier.system.skecobe.responses;

import com.cashier.system.skecobe.entities.InvoiceTour;
import com.cashier.system.skecobe.entities.Tour;
import com.cashier.system.skecobe.enums.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InvoiceTourResponse {

    private Long invoiceTourId;
    private Tour tourId;
    private int unitBus;
    private Long income;
    private int employee;
    private Status status;

    public static InvoiceTourResponse convertToResponse(InvoiceTour tour) {
        return InvoiceTourResponse.builder()
                .invoiceTourId(tour.getInvoiceTourId())
                .tourId(tour.getTourId())
                .unitBus(tour.getUnitBus())
                .income(tour.getIncome())
                .employee(tour.getEmployee())
                .status(tour.getStatus())
                .build();
    }

}
