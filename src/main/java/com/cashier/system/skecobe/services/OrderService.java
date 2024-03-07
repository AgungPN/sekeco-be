package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.InvoiceTour;
import com.cashier.system.skecobe.entities.Order;
import com.cashier.system.skecobe.entities.OrderDetails;
import com.cashier.system.skecobe.repositories.InvoiceTourRepository;
import com.cashier.system.skecobe.repositories.OrderRepository;
import com.cashier.system.skecobe.requests.invoiceTour.CreateInvoiceTourRequest;
import com.cashier.system.skecobe.requests.invoiceTour.UpdateInvoiceTourRequest;
import com.cashier.system.skecobe.requests.order.OrderDetailsRequest;
import com.cashier.system.skecobe.requests.order.OrderRequest;
import com.cashier.system.skecobe.responses.InvoiceTourResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ValidationService validationService;
    private final InvoiceTourService tourService;

    @Transactional()
    public void saveOrder(OrderRequest orderRequest){
        validationService.validate(orderRequest);

        Order order = new Order();

        order.setUserId(orderRequest.getUserId());
        if(orderRequest.getInvoiceTourId() != null){
            InvoiceTourResponse response = tourService.getOneById(orderRequest.getInvoiceTourId().getInvoiceTourId());
            UpdateInvoiceTourRequest request = new UpdateInvoiceTourRequest();
            request.setInvoiceTourId(response.getInvoiceTourId());
            request.setTourId(response.getTourId());
            request.setUnitBus(response.getUnitBus());
            request.setIncome(response.getIncome() + orderRequest.getTotalPrice());
            request.setEmployee(response.getEmployee());
            tourService.update(request);
        }
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
