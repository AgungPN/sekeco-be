package com.cashier.system.skecobe.controllers;

import com.cashier.system.skecobe.requests.supplier.CreateSupplierRequest;
import com.cashier.system.skecobe.requests.supplier.UpdateSupplierRequest;
import com.cashier.system.skecobe.responses.ResponseHandler;
import com.cashier.system.skecobe.services.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suppliers")
@AllArgsConstructor
public class SupplierController {

    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<Object> getList() {
        return ResponseHandler.responseWithoutMessage(supplierService.getList(), HttpStatus.OK);
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<Object> getOne(@PathVariable Long supplierId) {
        var supplierResponse = supplierService.findById(supplierId);

        return ResponseHandler.responseWithoutMessage(supplierResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateSupplierRequest supplierRequest) {
        var supplierResponse = supplierService.save(supplierRequest);
        return ResponseHandler.generateResponse(
                "Supplier created", supplierResponse, HttpStatus.CREATED
        );
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity<Object> update(
            @PathVariable Long supplierId, @RequestBody UpdateSupplierRequest supplierRequest) {
        var supplierResponse = supplierService.update(supplierId, supplierRequest);

        return ResponseHandler.generateResponse(
                "Supplier updated", supplierResponse, HttpStatus.OK
        );
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<Object> delete(@PathVariable Long supplierId) {
        supplierService.deleteById(supplierId);

        return ResponseHandler.successResponse(
                "Supplier deleted", HttpStatus.OK
        );
    }
}
