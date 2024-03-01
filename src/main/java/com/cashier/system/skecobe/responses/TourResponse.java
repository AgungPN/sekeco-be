package com.cashier.system.skecobe.responses;

import com.cashier.system.skecobe.entities.Tour;
import com.cashier.system.skecobe.enums.TourCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TourResponse {
    private Long tourId;
    private String name;
    private String address;
    private String phone;
    private TourCode tourCode;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public static TourResponse convertToResponse(Tour tour) {
        return TourResponse.builder()
                .tourId(tour.getTourId())
                .name(tour.getName())
                .address(tour.getAddress())
                .phone(tour.getPhone())
                .tourCode(tour.getTourCode())
                .createdAt(tour.getCreatedAt())
                .updatedAt(tour.getUpdatedAt())
                .build();
    }
}
