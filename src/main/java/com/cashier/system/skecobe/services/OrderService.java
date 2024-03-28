package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.*;
import com.cashier.system.skecobe.repositories.InvoiceTourRepository;
import com.cashier.system.skecobe.repositories.OrderRepository;
import com.cashier.system.skecobe.requests.invoiceTour.CreateInvoiceTourRequest;
import com.cashier.system.skecobe.requests.invoiceTour.UpdateInvoiceTourRequest;
import com.cashier.system.skecobe.requests.order.OrderDetailsRequest;
import com.cashier.system.skecobe.requests.order.OrderRequest;
import com.cashier.system.skecobe.responses.InvoiceTourResponse;
import com.cashier.system.skecobe.responses.OrderDetailsResponse;
import com.cashier.system.skecobe.responses.OrderResponse;
import com.cashier.system.skecobe.responses.UserResponse;
import com.cashier.system.skecobe.utils.ReportManager;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ValidationService validationService;
    private InvoiceTourService tourService;
    private CashierService cashierService;
    private ProductService productService;

    @Transactional()
    public byte[] saveOrder(OrderRequest orderRequest) throws JRException {
        validationService.validate(orderRequest);
        ReportManager.getInstance().compileReport();

        Order order = new Order();

        order.setUserId(cashierService.findById(orderRequest.getUserId()));
        order.setInvoiceTourId(invoiceTour(orderRequest.getInvoiceTourId(), orderRequest.getTotalPrice()));
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
        return ReportManager.getInstance().printReportPayment(OrderResponse.convertToResponse(order));
    }

    private InvoiceTour invoiceTour(Long invoiceTourId, Long totalPrice){
        if(invoiceTourId != null){
            InvoiceTour response = tourService.getOneById(invoiceTourId);
            UpdateInvoiceTourRequest request = new UpdateInvoiceTourRequest();
            request.setInvoiceTourId(response.getInvoiceTourId());
            request.setTourId(response.getTourId().getTourId());
            request.setUnitBus(response.getUnitBus());
            request.setIncome(response.getIncome() + totalPrice);
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
                .profitSharingAmount(request.getProfitSharingAmount())
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
