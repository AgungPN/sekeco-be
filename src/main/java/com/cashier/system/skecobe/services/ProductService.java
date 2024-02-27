package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.Product;
import com.cashier.system.skecobe.handlers.exceptions.NotFoundException;
import com.cashier.system.skecobe.helpers.Storage;
import com.cashier.system.skecobe.repositories.ProductRepository;
import com.cashier.system.skecobe.requests.CreateProductRequest;
import com.cashier.system.skecobe.requests.UpdateProductRequest;
import com.cashier.system.skecobe.responses.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    private ValidationService validationService;

    public List<ProductResponse> getList(String search) { // TODO: feature to search product by name
        var products = productRepository.findAll();
        return products.stream().map(ProductResponse::convertToResponse).toList();
    }

    public ProductResponse findById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductResponse::convertToResponse)
                .orElseThrow(() -> new NotFoundException("Product"));
    }

    public ProductResponse save(CreateProductRequest createProductRequest) throws IOException {
        String imageFiles = Storage.saveFileToStorage(createProductRequest.getImage(), "images/products");

        validationService.validate(createProductRequest);

        Product product = Product.builder()
                .name(createProductRequest.getName())
                .description(createProductRequest.getDescription())
                .price(createProductRequest.getPrice())
                .profitSharingPercentage(createProductRequest.getProfitSharingPercentage())
                .profitSharingAmount(createProductRequest.getProfitSharingAmount())
                .stock(createProductRequest.getStock())
                .image(imageFiles)
                .build();
        productRepository.save(product);

        return ProductResponse.convertToResponse(product);
    }

    public ProductResponse update(Long id, UpdateProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product"));

        validationService.validate(productRequest);

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setProfitSharingPercentage(productRequest.getProfitSharingPercentage());
        product.setProfitSharingAmount(productRequest.getProfitSharingAmount());
        product.setStock(productRequest.getStock());
        product.setImage(productRequest.getImageUrl());
        productRepository.save(product);

        return ProductResponse.convertToResponse(product);
    }

    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }
}
