package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.Order;
import com.cashier.system.skecobe.entities.OrderDetails;
import com.cashier.system.skecobe.repositories.OrderRepository;
import com.cashier.system.skecobe.requests.OrderDetailsRequest;
import com.cashier.system.skecobe.requests.OrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ValidationService validationService;
    private final Order order;

    public void saveOrder(OrderRequest orderRequest){
        validationService.validate(orderRequest);

        order.setCashierId(orderRequest.getCashierId());
        order.setInvoiceTourId(orderRequest.getInvoiceTourId());
        order.setTotalItems(orderRequest.getTotalItems());
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setAmount(orderRequest.getAmount());
        order.setRefund(orderRequest.getRefund());
        List<OrderDetails> orderDetailsList = orderRequest.getOrderDetailsRequests()
                .stream()
                .map((OrderDetailsRequest orderDetailsRequest) -> mapToOrderDetails(orderDetailsRequest, order))
                .toList();
        order.setOrderDetails(orderDetailsList);

        orderRepository.save(order);
    }

    private OrderDetails mapToOrderDetails(OrderDetailsRequest request, Order order){
        return OrderDetails.builder()
                .orderId(order)
                .productId(request.getProductId())
                .profitSharingAmount(request.getProfitSharingAmount())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .subtotal(request.getSubtotal())
                .build();
    }


}
