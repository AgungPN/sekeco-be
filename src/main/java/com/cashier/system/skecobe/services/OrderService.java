package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.*;
import com.cashier.system.skecobe.enums.ProfitShared;
import com.cashier.system.skecobe.handlers.exceptions.NotFoundException;
import com.cashier.system.skecobe.repositories.OrderRepository;
import com.cashier.system.skecobe.requests.invoiceTour.UpdateInvoiceTourRequest;
import com.cashier.system.skecobe.requests.order.OrderDetailsRequest;
import com.cashier.system.skecobe.requests.order.OrderRequest;
import com.cashier.system.skecobe.requests.product.UpdateProductRequest;
import com.cashier.system.skecobe.requests.purchases.ProductRequest;
import com.cashier.system.skecobe.responses.OrderDetailsResponse;
import com.cashier.system.skecobe.responses.OrderResponse;
import com.cashier.system.skecobe.responses.ProductResponse;
import com.cashier.system.skecobe.utils.ReportManager;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ValidationService validationService;
    @Autowired
    private InvoiceTourService tourService;
    private CashierService cashierService;
    private ProductService productService;

    @Transactional()
    public byte[] saveOrder(OrderRequest orderRequest) throws JRException {
        validationService.validate(orderRequest);
        ReportManager.getInstance().compileReport();

        Order order = new Order();
        InvoiceTour getInvoiceTour = getInvoiceTour(orderRequest.getInvoiceTourId());
        if(getInvoiceTour != null){
            order.setInvoiceTourId(getInvoiceTour);
        }
        order.setUserId(cashierService.findById(orderRequest.getUserId()));
        order.setTotalItems(orderRequest.getTotalItems());
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setAmount(orderRequest.getAmount());
        order.setRefund(orderRequest.getRefund());
        List<OrderDetails> orderDetailsList = orderRequest.getOrderDetailsRequests()
                .stream()
                .map((OrderDetailsRequest orderDetailsRequest) -> mapToOrderDetails(orderDetailsRequest, order))
                .toList();
        order.setOrderDetails(orderDetailsList);
        for (OrderDetailsRequest request : orderRequest.getOrderDetailsRequests()) {
            Product product = productService.getOneById(request.getProductId());
            UpdateProductRequest updateProductRequest = new UpdateProductRequest();
            updateProductRequest.setId(product.getProductId());
            updateProductRequest.setBrand(product.getBrand());
            updateProductRequest.setBarcode(product.getBarcode());
            updateProductRequest.setName(product.getName());
            updateProductRequest.setProfitSharing(product.getProfitSharing());
            updateProductRequest.setProfitSharedType(product.getProfitSharedType().toString());
            updateProductRequest.setStock(product.getStock() - request.getQuantity());
            updateProductRequest.setPrice(product.getPrice());
            productService.update(product.getProductId(), updateProductRequest);
        }
        orderRepository.save(order);
        return ReportManager.getInstance().printReportPayment(OrderResponse.convertToResponse(order));
    }
    public byte[] getOrder(Long orderId) throws JRException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Product"));
        ReportManager.getInstance().compileReport();

        return ReportManager.getInstance().printReportPayment(OrderResponse.convertToResponse(order));
    }

    private InvoiceTour getInvoiceTour(Long invoiceTourId){
        if(invoiceTourId != -1){
            InvoiceTour response = tourService.getOneById(invoiceTourId);
            UpdateInvoiceTourRequest request = new UpdateInvoiceTourRequest();
            request.setInvoiceTourId(response.getInvoiceTourId());
            request.setTourId(response.getTourId().getTourId());
            request.setUnitBus(response.getUnitBus());
            request.setProfitSharing(response.getProfitSharing());
            request.setEmployee(response.getEmployee());
            tourService.update(request);
            return response;
        }else {
            return null;
        }

    }

    private OrderDetails mapToOrderDetails(OrderDetailsRequest request, Order order ){
        return OrderDetails.builder()
                .orderId(order)
                .productId(productService.getOneById(request.getProductId()))
                .profitSharing(request.getProfitSharing())
                .profitSharedType(ProfitShared.valueOf(request.getProfitSharedType()))
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .subtotal(request.getSubtotal())
                .build();
    }

    public List<OrderResponse> getOrderByInvoiceTourId(Long invoiceTourId){
        InvoiceTour tour = tourService.getOneById(invoiceTourId);
        List<Order>  orders = orderRepository.findByInvoiceTourId(tour);
        return orders.stream().map(OrderResponse::convertToResponse).toList();
    }

}
