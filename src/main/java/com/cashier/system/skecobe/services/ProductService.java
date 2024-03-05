package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.Product;
import com.cashier.system.skecobe.handlers.exceptions.NotFoundException;
import com.cashier.system.skecobe.repositories.ProductRepository;
import com.cashier.system.skecobe.requests.product.CreateProductRequest;
import com.cashier.system.skecobe.requests.product.UpdateProductRequest;
import com.cashier.system.skecobe.responses.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    private ValidationService validationService;

    public Page<ProductResponse> getList(Pageable pageable) {
        var products = productRepository.findAll(pageable);
        return products.map(ProductResponse::convertToResponse);
    }

    public ProductResponse getOneById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductResponse::convertToResponse)
                .orElseThrow(() -> new NotFoundException("Product"));
    }

    public ProductResponse getOneByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode)
                .map(ProductResponse::convertToResponse)
                .orElseThrow(() -> new NotFoundException("Product"));
    }

    public ProductResponse save(CreateProductRequest createProductRequest) throws IOException {
        validationService.validate(createProductRequest);

        Product product = Product.builder()
                .barcode(createProductRequest.getBarcode())
                .name(createProductRequest.getName())
                .brand(createProductRequest.getBrand())
                .profitSharingAmount(createProductRequest.getProfitSharingAmount())
                .price(createProductRequest.getPrice())
                .stock(createProductRequest.getStock())
                .build();
        productRepository.save(product);

        return ProductResponse.convertToResponse(product);
    }

    public ProductResponse update(Long id, UpdateProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product"));

        validationService.validate(productRequest);

        product.setName(productRequest.getName());
        product.setBarcode(productRequest.getBarcode());
        product.setBrand(productRequest.getBrand());
        product.setProfitSharingAmount(productRequest.getProfitSharingAmount());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        productRepository.save(product);

        return ProductResponse.convertToResponse(product);
    }

    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }
}
