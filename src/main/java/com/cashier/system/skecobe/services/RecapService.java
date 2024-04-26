package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.Product;
import com.cashier.system.skecobe.enums.Status;
import com.cashier.system.skecobe.repositories.*;
import com.cashier.system.skecobe.responses.InvoiceTourResponse;
import com.cashier.system.skecobe.responses.OrderResponse;
import com.cashier.system.skecobe.responses.ProductResponse;
import com.cashier.system.skecobe.responses.PurchaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RecapService {

    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private InvoiceTourRepository tourRepository;
    private PurchaseRepository purchaseRepository;
    private ProductRepository productRepository;

    public Page<OrderResponse> getOrder(Pageable pageable) {
        var order = orderRepository.findAll(pageable);
        return order.map(OrderResponse::convertToResponse);
    }

    public Page<InvoiceTourResponse> getInvoiceTour(Pageable pageable) {
        var invoiceTour = tourRepository.findByStatus(pageable, Status.PREVIUSLY);
        return invoiceTour.map(InvoiceTourResponse::convertToResponse);
    }

    public Page<PurchaseResponse> getPurchase(Pageable pageable) {
        var purchase = purchaseRepository.findAll(pageable);
        return purchase.map(PurchaseResponse::convertToResponse);
    }

    public Page<OrderResponse> getOrder(Pageable pageable, LocalDate startDate, LocalDate endDate) {
        var order = orderRepository.findByCreatedAtBetween(pageable, startDate, endDate);
        return order.map(OrderResponse::convertToResponse);
    }

    public Page<InvoiceTourResponse> getInvoiceTour(Pageable pageable, LocalDate startDate, LocalDate endDate) {
        var invoiceTour = tourRepository.findByStatusAndCreatedAtBetween(Status.PREVIUSLY, pageable, startDate, endDate);
        return invoiceTour.map(InvoiceTourResponse::convertToResponse);
    }

    public Page<PurchaseResponse> getPurchase(Pageable pageable, LocalDate startDate, LocalDate endDate) {
        var purchase = purchaseRepository.findByCreatedAtBetween(pageable, startDate, endDate);
        return purchase.map(PurchaseResponse::convertToResponse);
    }

    public List<ProductResponse> getProductOrder(Pageable pageable, LocalDate startDate, LocalDate endDate) {
        Map<Product, Integer> productOrderCounts = new HashMap<>();
        var orderDetails = orderDetailRepository.findByCreatedAtBetween(startDate, endDate);

        for (var orderDetail : orderDetails) {
            var product = orderDetail.getProductId();
            product.setPrice(orderDetail.getPrice());
            if (productOrderCounts.containsKey(product)) {
                productOrderCounts.put(product, productOrderCounts.get(product) + orderDetail.getQuantity());
            } else {
                productOrderCounts.put(product, orderDetail.getQuantity());
            }
        }

        List<ProductResponse> productResponses = new ArrayList<>();
        for (var productEntry : productOrderCounts.entrySet()) {
            var product = productEntry.getKey();
            var count = productEntry.getValue();
            // Buat ProductResponse untuk setiap produk
            var productResponse = ProductResponse.convertToResponse(product);
            productResponse.setCount(count);

            // Tambahkan ProductResponse ke daftar respons
            productResponses.add(productResponse);
        }

        return productResponses;
    }

    public List<ProductResponse> getProductOrder(Pageable pageable) {
        Map<Product, Integer> productOrderCounts = new HashMap<>();
        var orderDetails = orderDetailRepository.findAll();

        for (var orderDetail : orderDetails) {
            var product = orderDetail.getProductId();
            product.setPrice(orderDetail.getPrice());
            if (productOrderCounts.containsKey(product)) {
                productOrderCounts.put(product, productOrderCounts.get(product) + orderDetail.getQuantity());
            } else {
                productOrderCounts.put(product, orderDetail.getQuantity());
            }
        }

        List<ProductResponse> productResponses = new ArrayList<>();
        for (var productEntry : productOrderCounts.entrySet()) {
            var product = productEntry.getKey();
            var count = productEntry.getValue();

            var productResponse = ProductResponse.convertToResponse(product);
            productResponse.setCount(count);

            productResponses.add(productResponse);
        }

        return productResponses;
    }
}
