package com.cashier.system.skecobe.controllers;

import com.cashier.system.skecobe.requests.purchases.CreatePurchaseRequest;
import com.cashier.system.skecobe.responses.ResponseHandler;
import com.cashier.system.skecobe.services.ProductService;
import com.cashier.system.skecobe.services.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/admin/purchases")
@AllArgsConstructor
public class PurchaseController {

    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreatePurchaseRequest productRequest) throws Exception {
         purchaseService.save(productRequest);
        return ResponseHandler.generateResponse(
                "Product created", productRequest, HttpStatus.CREATED
        );
    }

}
