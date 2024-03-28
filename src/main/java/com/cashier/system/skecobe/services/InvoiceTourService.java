package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.InvoiceTour;
import com.cashier.system.skecobe.entities.Tour;
import com.cashier.system.skecobe.enums.Status;
import com.cashier.system.skecobe.handlers.exceptions.NotFoundException;
import com.cashier.system.skecobe.repositories.InvoiceTourRepository;
import com.cashier.system.skecobe.requests.invoiceTour.CreateInvoiceTourRequest;
import com.cashier.system.skecobe.requests.invoiceTour.UpdateInvoiceTourRequest;
import com.cashier.system.skecobe.responses.InvoiceTourResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvoiceTourService {

    private final InvoiceTourRepository invoiceTourRepository;
    private final TourService tourService;
    private final ValidationService validationService;

    public List<InvoiceTourResponse> getAll(){
        var response = invoiceTourRepository.findAll();
        return response.stream().map(InvoiceTourResponse::convertToResponse).toList();
    }

    public InvoiceTour getOneById(Long invoiceTourId){
        return invoiceTourRepository.findById(invoiceTourId)
                .orElseThrow(() -> new NotFoundException("Invoice Tour"));
    }

    public InvoiceTourResponse getTourWithStatusAndTourId(Status status, Long tourId){
        Tour tour = tourService.findById(tourId);
        var response = invoiceTourRepository.findByStatusOrTourId(status, tour);
        return (response != null)
                ? InvoiceTourResponse.convertToResponse(response)
                : null;
    }
    public List<InvoiceTourResponse> getTourWithStatus(Status status) {
        List<InvoiceTour> invoiceTours = invoiceTourRepository.findByStatus(status);
        return invoiceTours.stream().map(InvoiceTourResponse::convertToResponse).toList();
    }

    public InvoiceTourResponse save(CreateInvoiceTourRequest request){
        validationService.validate(request);

        Tour tour = tourService.findById(request.getTourId());

        InvoiceTour invoiceTour = InvoiceTour.builder()
                .tourId(tour)
                .unitBus(request.getUnitBus())
                .income(0L)
                .employee(request.getEmployee())
                .status(Status.NOW)
                .build();

        invoiceTourRepository.save(invoiceTour);

        return InvoiceTourResponse.convertToResponse(invoiceTour);
    }

    public InvoiceTourResponse update(UpdateInvoiceTourRequest request){
        InvoiceTour invoiceTour = invoiceTourRepository.findById(request.getInvoiceTourId())
                .orElseThrow(() -> new NotFoundException("Product"));

        validationService.validate(request);

        invoiceTour.setTourId(tourService.findById(request.getTourId()));
        invoiceTour.setUnitBus(request.getUnitBus());
        invoiceTour.setIncome(request.getIncome());
        invoiceTour.setEmployee(request.getEmployee());
        invoiceTourRepository.save(invoiceTour);

        return InvoiceTourResponse.convertToResponse(invoiceTour);
    }

    public void deleteById(Long productId) {
        invoiceTourRepository.deleteById(productId);
    }
}
