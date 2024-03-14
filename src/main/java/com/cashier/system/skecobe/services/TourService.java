package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.Tour;
import com.cashier.system.skecobe.enums.TourCode;
import com.cashier.system.skecobe.handlers.exceptions.NotFoundException;
import com.cashier.system.skecobe.repositories.TourRepository;
import com.cashier.system.skecobe.requests.tour.CreateTourRequest;
import com.cashier.system.skecobe.requests.tour.UpdateTourRequest;
import com.cashier.system.skecobe.responses.TourResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TourService {
    private TourRepository tourRepository;
    private ValidationService validationService;


    public Page<TourResponse> getList(Pageable pageable) {
        var tours = tourRepository.findAll(pageable);
        return tours.map(TourResponse::convertToResponse);
    }

    public Page<TourResponse> getList(String search, Pageable pageable) {
        var tours = tourRepository.findByNameContainingOrPhoneContainingOrderByName(search, search, pageable);
        return tours.map(TourResponse::convertToResponse);
    }

    public Tour findById(Long tourId) {
        return tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Tour"));
    }

    public TourResponse save(CreateTourRequest createTourRequest) {
        validationService.validate(createTourRequest);

        Tour tour = Tour.builder()
                .name(createTourRequest.getName())
                .address(createTourRequest.getAddress())
                .phone(createTourRequest.getPhone())
                .build();

        tourRepository.save(tour);

        return TourResponse.convertToResponse(tour);
    }

    public TourResponse update(Long id, UpdateTourRequest tourRequest) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tour"));

        validationService.validate(tourRequest);

        tour.setName(tourRequest.getName());
        tour.setAddress(tourRequest.getAddress());
        tour.setPhone(tourRequest.getPhone());
        tourRepository.save(tour);

        return TourResponse.convertToResponse(tour);
    }

    public void deleteById(Long tourId) {
        tourRepository.deleteById(tourId);
    }
}
