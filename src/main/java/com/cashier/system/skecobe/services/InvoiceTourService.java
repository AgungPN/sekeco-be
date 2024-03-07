package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.InvoiceTour;
import com.cashier.system.skecobe.entities.Product;
import com.cashier.system.skecobe.handlers.exceptions.NotFoundException;
import com.cashier.system.skecobe.repositories.InvoiceTourRepository;
import com.cashier.system.skecobe.requests.invoiceTour.CreateInvoiceTourRequest;
import com.cashier.system.skecobe.requests.invoiceTour.UpdateInvoiceTourRequest;
import com.cashier.system.skecobe.responses.InvoiceTourResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvoiceTourService {

    private final InvoiceTourRepository tourRepository;
    private final ValidationService validationService;

    public InvoiceTourResponse getOneById(Long invoiceTourId){
        return tourRepository.findById(invoiceTourId)
                .map(InvoiceTourResponse::convertToResponse)
                .orElseThrow(() -> new NotFoundException("Invoice Tour"));
    }

    public InvoiceTourResponse save(CreateInvoiceTourRequest request){
        validationService.validate(request);
        InvoiceTour invoiceTour = InvoiceTour.builder()
                .tourId(request.getTourId())
                .unitBus(request.getUnitBus())
                .income(0L)
                .employee(request.getEmployee())
                .build();

        tourRepository.save(invoiceTour);

        return InvoiceTourResponse.convertToResponse(invoiceTour);
    }

    public InvoiceTourResponse update(UpdateInvoiceTourRequest request){
        InvoiceTour invoiceTour = tourRepository.findById(request.getInvoiceTourId())
                .orElseThrow(() -> new NotFoundException("Product"));

        validationService.validate(request);

        invoiceTour.setTourId(request.getTourId());
        invoiceTour.setUnitBus(request.getUnitBus());
        invoiceTour.setIncome(request.getIncome());
        invoiceTour.setEmployee(request.getEmployee());
        tourRepository.save(invoiceTour);

        return InvoiceTourResponse.convertToResponse(invoiceTour);
    }

    public void deleteById(Long productId) {
        tourRepository.deleteById(productId);
    }
}
