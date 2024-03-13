package com.cashier.system.skecobe.controllers;

import com.cashier.system.skecobe.requests.supplier.CreateSupplierRequest;
import com.cashier.system.skecobe.requests.supplier.UpdateSupplierRequest;
import com.cashier.system.skecobe.responses.ResponseHandler;
import com.cashier.system.skecobe.responses.SupplierResponse;
import com.cashier.system.skecobe.services.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
@AllArgsConstructor
public class SupplierController {

    private SupplierService supplierService;

    @GetMapping
    public Page<SupplierResponse> getList(
            @RequestParam(value = "search", required = false) String search,
            @PageableDefault() Pageable pageable) {
        return search == null || search.isEmpty()
                ? supplierService.getList(pageable)
                : supplierService.getList(search, pageable);
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<Object> getOne(@PathVariable Long supplierId) {
        var supplierResponse = supplierService.getOneById(supplierId);

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
