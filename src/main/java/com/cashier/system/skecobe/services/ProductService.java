package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.Product;
import com.cashier.system.skecobe.enums.ProfitShared;
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
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    private ValidationService validationService;

    public Page<ProductResponse> getList(Pageable pageable) {
        var products = productRepository.findAll(pageable);
        return products.map(ProductResponse::convertToResponse);
    }

    public Page<ProductResponse> getList(String search, Pageable pageable) {
        var products = productRepository.findByNameContainingOrBrandContainingOrBarcodeContainingOrderByName(search, search, search, pageable);
        return products.map(ProductResponse::convertToResponse);
    }

    public List<ProductResponse> getAll() {
        var products = productRepository.findAll();
        return products.stream().map(ProductResponse::convertToResponse).toList();
    }

    public Product getOneById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product"));
    }

        public ProductResponse getOneByBarcode(String barcode) {
            return  productRepository.findByBarcode(barcode)
                    .map(ProductResponse::convertToResponse)
                    .orElseThrow(() -> new NotFoundException("Product"));
        }

    public ProductResponse save(CreateProductRequest createProductRequest) throws IOException {
        validationService.validate(createProductRequest);

        Product product = Product.builder()
                .barcode(createProductRequest.getBarcode())
                .name(createProductRequest.getName())
                .brand(createProductRequest.getBrand())
                .profitSharing(createProductRequest.getProfitSharingAmount())
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
        product.setProfitSharing(productRequest.getProfitSharing());
        product.setProfitSharedType(ProfitShared.valueOf(productRequest.getProfitSharedType()));
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        productRepository.save(product);

        return ProductResponse.convertToResponse(product);
    }

    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }
}
