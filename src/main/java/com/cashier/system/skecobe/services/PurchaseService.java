package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.Product;
import com.cashier.system.skecobe.entities.Purchase;
import com.cashier.system.skecobe.entities.PurchaseDetail;
import com.cashier.system.skecobe.entities.Supplier;
import com.cashier.system.skecobe.enums.ProfitShared;
import com.cashier.system.skecobe.handlers.exceptions.MultipleErrorsException;
import com.cashier.system.skecobe.handlers.exceptions.NotFoundException;
import com.cashier.system.skecobe.repositories.ProductRepository;
import com.cashier.system.skecobe.repositories.PurchaseDetailRepository;
import com.cashier.system.skecobe.repositories.PurchaseRepository;
import com.cashier.system.skecobe.requests.purchases.CreatePurchaseRequest;
import com.cashier.system.skecobe.requests.purchases.product.ExistingProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
@AllArgsConstructor
public class PurchaseService {
    private ProductRepository productRepository;
    private ValidationService validationService;
    private SupplierService supplierService;
    private PurchaseRepository purchaseRepository;
    private PurchaseDetailRepository purchaseDetailRepository;

    public void save(CreatePurchaseRequest productRequest) throws Exception {

        AtomicLong totalPricePurchase = new AtomicLong(0L);

        if (
                (productRequest.getExistingProducts() != null && productRequest.getExistingProducts().isEmpty()) && (productRequest.getNewProducts() != null && productRequest.getNewProducts().isEmpty())
        ) {
            throw new Exception("You must provide at least one product");
        }

        validationService.validate(productRequest);

        Supplier supplier = supplierService.findById(productRequest.getSupplierId());
        List<Product> newProducts = new ArrayList<>();
        List<Product> existingProducts = productRepository.findAllById(
                productRequest.getExistingProducts() == null ? Collections.emptyList()
                        : productRequest.getExistingProducts().parallelStream().map(ExistingProductRequest::getProductId).toList()
        );
        List<PurchaseDetail> purchaseDetails = new ArrayList<>();
        Purchase purchase = Purchase.builder().supplier(supplier)
                .totalItems(
                        Optional.ofNullable(productRequest.getExistingProducts()).map(List::size).orElse(0) +
                                Optional.ofNullable(productRequest.getNewProducts()).map(List::size).orElse(0)
                )
                .amount(productRequest.getAmount())
                .build();


        if (productRequest.getNewProducts() != null) {
            productRequest.getNewProducts().forEach(newProductDTO -> {
                Product product = Product.builder()
                        .barcode(newProductDTO.getBarcode())
                        .name(newProductDTO.getName())
                        .brand(newProductDTO.getBrand())
                        .profitSharedType(ProfitShared.valueOf(newProductDTO.getProfitSharedType()))
                        .profitSharing(newProductDTO.getProfitSharing())
                        .price(newProductDTO.getPriceSell())
                        .stock(newProductDTO.getQuantity())
                        .build();
                newProducts.add(product);

                long subtotal = newProductDTO.getQuantity() * newProductDTO.getPriceBuy();
                totalPricePurchase.addAndGet(subtotal);

                purchaseDetails.add(
                        PurchaseDetail.builder()
                                .product(product)
                                .purchase(purchase)
                                .price(newProductDTO.getPriceBuy())
                                .quantity(newProductDTO.getQuantity())
                                .subtotal(subtotal)
                                .build()
                );
            });
            List<Product> byBarcodeIn = productRepository.findByBarcodeIn(newProducts.parallelStream().map(Product::getBarcode).toList());
            if (!byBarcodeIn.isEmpty()) {
                throw new MultipleErrorsException("Product", byBarcodeIn.parallelStream().map(product -> product.getBarcode() + " already exists").toList());
            }
        }

        if (productRequest.getExistingProducts() != null) {
            productRequest.getExistingProducts().forEach(existingProductDTO -> {
                Product product = existingProducts.stream()
                        .filter(p -> p.getProductId().equals(existingProductDTO.getProductId()))
                        .findFirst()
                        .orElseThrow(() -> new NotFoundException("Product"));

                product.setStock(product.getStock() + existingProductDTO.getQuantity());
                product.setProfitSharedType(ProfitShared.valueOf(existingProductDTO.getProfitSharedType()));
                product.setProfitSharing(existingProductDTO.getProfitSharing());
                product.setPrice(existingProductDTO.getPriceSell());
                productRepository.save(product);

                long subtotal = existingProductDTO.getQuantity() * product.getPrice();
                totalPricePurchase.addAndGet(subtotal);

                purchaseDetails.add(
                        PurchaseDetail.builder()
                                .product(product)
                                .purchase(purchase)
                                .price(product.getPrice())
                                .quantity(existingProductDTO.getQuantity())
                                .subtotal(subtotal)
                                .build()
                );
            });
        }

        purchase.setTotalPrice(totalPricePurchase.get());
        purchaseRepository.save(purchase);
        productRepository.saveAll(newProducts);
        purchaseDetailRepository.saveAll(purchaseDetails);
    }
}
