package com.cashier.system.skecobe.responses;

import com.cashier.system.skecobe.entities.InvoiceTour;
import com.cashier.system.skecobe.entities.Tour;
import com.cashier.system.skecobe.enums.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class InvoiceTourResponse {

    private Long invoiceTourId;
    private Tour tourId;
    private int unitBus;
    private Long profitSharing;
    private int employee;
    private Status status;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public static InvoiceTourResponse convertToResponse(InvoiceTour tour) {
        if(tour == null){
            return null;
        }
        return InvoiceTourResponse.builder()
                .invoiceTourId(tour.getInvoiceTourId())
                .tourId(tour.getTourId())
                .unitBus(tour.getUnitBus())
                .profitSharing(tour.getProfitSharing())
                .employee(tour.getEmployee())
                .status(tour.getStatus())
                .createdAt(tour.getCreatedAt())
                .updatedAt(tour.getLastModifiedAt())
                .build();
    }

}
