package com.cashier.system.skecobe.controllers;

import com.cashier.system.skecobe.requests.CreateProductRequest;
import com.cashier.system.skecobe.requests.UpdateProductRequest;
import com.cashier.system.skecobe.responses.ProductResponse;
import com.cashier.system.skecobe.responses.ResponseHandler;
import com.cashier.system.skecobe.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public ResponseEntity<Object> getList() {
        return ResponseHandler.responseWithoutMessage(productService.getList(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> getOne(@PathVariable Long productId) {
        var productResponse = productService.findById(productId);

        return ResponseHandler.responseWithoutMessage(productResponse, HttpStatus.OK);
    }

    @GetMapping("/barcode")
    public ResponseEntity<Object> getDataByBarcode(@RequestParam("barcode") String barcode) {
        var productResponse = productService.findByBarcode(barcode);

        return ResponseHandler.responseWithoutMessage(productResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@ModelAttribute CreateProductRequest productRequest) throws IOException {
        var productResponse = productService.save(productRequest);
        return ResponseHandler.generateResponse(
                "Product created", productResponse, HttpStatus.CREATED
        );
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<Object> update(
            @PathVariable Long productId, @RequestBody UpdateProductRequest productRequest) {
        var productResponse = productService.update(productId, productRequest);

        return ResponseHandler.generateResponse(
                "Product updated", productResponse, HttpStatus.OK
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> delete(@PathVariable Long productId) {
        productService.deleteById(productId);

        return ResponseHandler.successResponse(
                "Product deleted", HttpStatus.OK
        );
    }
}
