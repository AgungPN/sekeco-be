package com.cashier.system.skecobe.controllers;

import com.cashier.system.skecobe.requests.OrderRequest;
import com.cashier.system.skecobe.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.saveOrder(orderRequest);
        return "Order Placed Successfully";
    }
}
