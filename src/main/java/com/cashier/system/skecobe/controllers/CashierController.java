package com.cashier.system.skecobe.controllers;

import com.cashier.system.skecobe.requests.users.CreateCashierRequest;
import com.cashier.system.skecobe.requests.users.UpdateCashierRequest;
import com.cashier.system.skecobe.responses.ResponseHandler;
import com.cashier.system.skecobe.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cashiers")
@AllArgsConstructor
public class CashierController {

    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getList() {
        return ResponseHandler.responseWithoutMessage(userService.getListCashier(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOne(@PathVariable Long userId) {
        var userResponse = userService.findById(userId);

        return ResponseHandler.responseWithoutMessage(userResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateCashierRequest userRequest) throws BadRequestException {
        var userResponse = userService.save(userRequest);
        return ResponseHandler.generateResponse(
                "Cashier created", userResponse, HttpStatus.CREATED
        );
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> update(
            @PathVariable Long userId, @RequestBody UpdateCashierRequest userRequest) throws BadRequestException {
        var userResponse = userService.update(userId, userRequest);

        return ResponseHandler.generateResponse(
                "Cashier updated", userResponse, HttpStatus.OK
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable Long userId) {
        userService.deleteById(userId);

        return ResponseHandler.successResponse(
                "Cashier deleted", HttpStatus.OK
        );
    }
}
