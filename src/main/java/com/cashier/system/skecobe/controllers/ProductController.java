package com.cashier.system.skecobe.controllers;

import com.cashier.system.skecobe.requests.product.CreateProductRequest;
import com.cashier.system.skecobe.requests.product.UpdateProductRequest;
import com.cashier.system.skecobe.responses.ProductResponse;
import com.cashier.system.skecobe.responses.ResponseHandler;
import com.cashier.system.skecobe.security.UserPrincipal;
import com.cashier.system.skecobe.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public Page<ProductResponse> getList(@PageableDefault() Pageable pageable) {
        return productService.getList(pageable);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> getOne(@PathVariable Long productId) {
        var productResponse = productService.getOneById(productId);

        return ResponseHandler.responseWithoutMessage(productResponse, HttpStatus.OK);
    }

    @GetMapping("/barcode")
    public ResponseEntity<Object> getDataByBarcode(@RequestParam("barcode") String barcode) {
        var productResponse = productService.getOneByBarcode(barcode);

        return ResponseHandler.responseWithoutMessage(productResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateProductRequest productRequest) throws IOException {
        var productResponse = productService.save(productRequest);
        return ResponseHandler.generateResponse(
                "Product created", productResponse, HttpStatus.CREATED
        );
    }

    @PutMapping("/{productId}")
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
