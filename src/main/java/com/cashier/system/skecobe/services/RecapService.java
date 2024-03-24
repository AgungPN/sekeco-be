package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.repositories.InvoiceTourRepository;
import com.cashier.system.skecobe.repositories.OrderRepository;
import com.cashier.system.skecobe.repositories.PurchaseRepository;
import com.cashier.system.skecobe.responses.InvoiceTourResponse;
import com.cashier.system.skecobe.responses.OrderResponse;
import com.cashier.system.skecobe.responses.PurchaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class RecapService {

    private OrderRepository orderRepository;
    private InvoiceTourRepository tourRepository;
    private PurchaseRepository purchaseRepository;

    public Page<OrderResponse> getOrder(Pageable pageable){
        var order = orderRepository.findAll(pageable);
        return order.map(OrderResponse::convertToResponse);
    }
    public Page<InvoiceTourResponse> getInvoiceTour(Pageable pageable){
        var invoiceTour = tourRepository.findAll(pageable);
        return invoiceTour.map(InvoiceTourResponse::convertToResponse);
    }
    public Page<PurchaseResponse> getPurchase(Pageable pageable){
        var purchase = purchaseRepository.findAll(pageable);
        return purchase.map(PurchaseResponse::convertToResponse);
    }

    public Page<OrderResponse> getOrder(Pageable pageable, LocalDate startDate, LocalDate endDate){
        var order = orderRepository.findByCreatedAtBetween(pageable, startDate, endDate);
        return order.map(OrderResponse::convertToResponse);
    }
    public Page<InvoiceTourResponse> getInvoiceTour(Pageable pageable, LocalDate startDate, LocalDate endDate){
        var invoiceTour = tourRepository.findByCreatedAtBetween(pageable, startDate, endDate);
        return invoiceTour.map(InvoiceTourResponse::convertToResponse);
    }
    public Page<PurchaseResponse> getPurchase(Pageable pageable, LocalDate startDate, LocalDate endDate){
        var purchase = purchaseRepository.findByCreatedAtBetween(pageable, startDate, endDate);
        return purchase.map(PurchaseResponse::convertToResponse);
    }
}
