package com.cashier.system.skecobe.controllers;

import com.cashier.system.skecobe.enums.Status;
import com.cashier.system.skecobe.requests.invoiceTour.CreateInvoiceTourRequest;
import com.cashier.system.skecobe.requests.invoiceTour.UpdateInvoiceTourRequest;
import com.cashier.system.skecobe.responses.InvoiceTourResponse;
import com.cashier.system.skecobe.responses.ResponseHandler;
import com.cashier.system.skecobe.services.InvoiceTourService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/invoice/tour")
@AllArgsConstructor
public class InvoiceTourController {

    private final InvoiceTourService tourService;

    @GetMapping("/status")
    public ResponseEntity<Object> getList(@RequestParam("status") String status){
        var response = tourService.getTourWithStatus(Status.valueOf(status));
        return ResponseHandler.responseWithoutMessage(response, HttpStatus.OK);
    }

    @GetMapping("/status/tourID")
    public ResponseEntity<Object> getStatusInTourId(@RequestParam("status") String status, @RequestParam("tourId") Long tourId){
        var response = tourService.getTourWithStatusAndTourId(Status.valueOf(status), tourId);
        return ResponseHandler.responseWithoutMessage(response, HttpStatus.OK);
    }

    @GetMapping("/{invoiceTourId}")
    public ResponseEntity<Object> getOne(@PathVariable Long invoiceTourId) {
        var response = tourService.getOneById(invoiceTourId);

        return ResponseHandler.responseWithoutMessage(response, HttpStatus.OK);
    }
    @PostMapping("/save")
    public ResponseEntity<Object> create(@RequestBody CreateInvoiceTourRequest tourRequest) throws IOException{
        var response = tourService.save(tourRequest);
        return ResponseHandler.generateResponse(
                "Invoice Tour created", response, HttpStatus.CREATED
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody UpdateInvoiceTourRequest tourRequest) throws IOException{
        var response = tourService.update(tourRequest);
        return ResponseHandler.generateResponse(
                "Invoice Tour update", response, HttpStatus.OK
        );
    }

    @DeleteMapping("/{invoiceTourId}")
    public ResponseEntity<Object> delete(@PathVariable Long invoiceTourId) {
        tourService.deleteById(invoiceTourId);

        return ResponseHandler.successResponse(
                "PInvoice Tour deleted", HttpStatus.OK
        );
    }

}
