package com.cashier.system.skecobe.controllers;

import com.cashier.system.skecobe.requests.order.OrderRequest;
import com.cashier.system.skecobe.responses.ResponseHandler;
import com.cashier.system.skecobe.services.OrderService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;

import java.io.ByteArrayInputStream;
import java.util.Locale;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final TemplateEngine templateEngine;
    private final OrderService orderService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InputStreamResource> placeOrder(@RequestBody OrderRequest orderRequest, Locale locale) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "attachment; filename=report.pdf");

            byte[] reportBytes = orderService.saveOrder(orderRequest);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_PDF_VALUE))
                    .body(new InputStreamResource(new ByteArrayInputStream(reportBytes)));
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/print")
    public ResponseEntity<InputStreamResource> getOrderPDF(@RequestParam Long orderId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "attachment; filename=report.pdf");

            byte[] reportBytes = orderService.getOrder(orderId);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_PDF_VALUE))
                    .body(new InputStreamResource(new ByteArrayInputStream(reportBytes)));
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/invoiceTourId")
    public ResponseEntity<Object> getOrderByInvoiceTourId(@RequestParam("invoiceTourId") Long invoiceTourId){
        var response = orderService.getOrderByInvoiceTourId(invoiceTourId);
        return ResponseHandler.responseWithoutMessage(response, HttpStatus.OK);
    }
}
