package com.cashier.system.skecobe.controllers;

import com.cashier.system.skecobe.responses.InvoiceTourResponse;
import com.cashier.system.skecobe.responses.OrderResponse;
import com.cashier.system.skecobe.responses.ProductResponse;
import com.cashier.system.skecobe.responses.PurchaseResponse;
import com.cashier.system.skecobe.services.RecapService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/recap")
@AllArgsConstructor
public class RecapController {

    private RecapService recapService;

    @GetMapping("/order")
    public Page<OrderResponse> getOrder(
            @PageableDefault() Pageable pageable,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return (startDate != null && endDate != null)
                ? recapService.getOrder(pageable, startDate, endDate)
                : recapService.getOrder(pageable);
    }

    @GetMapping("/invoice")
    public Page<InvoiceTourResponse> getInvoiceTour(
            @PageableDefault() Pageable pageable,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return (startDate != null && endDate != null)
                ? recapService.getInvoiceTour(pageable, startDate, endDate)
                : recapService.getInvoiceTour(pageable);
    }

    @GetMapping("/purchase")
    public Page<PurchaseResponse> getPurchase(
            @PageableDefault() Pageable pageable,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return (startDate != null && endDate != null)
                ? recapService.getPurchase(pageable, startDate, endDate)
                : recapService.getPurchase(pageable);
    }

    @GetMapping("/products")
    public List<ProductResponse> getProducts(
            @PageableDefault() Pageable pageable,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return (startDate != null && endDate != null)
                ? recapService.getProductOrder(pageable, startDate, endDate)
                : recapService.getProductOrder(pageable);
    }
}
