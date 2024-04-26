package com.cashier.system.skecobe.services;

import com.cashier.system.skecobe.entities.InvoiceTour;
import com.cashier.system.skecobe.entities.Order;
import com.cashier.system.skecobe.entities.Tour;
import com.cashier.system.skecobe.enums.ProfitShared;
import com.cashier.system.skecobe.enums.Status;
import com.cashier.system.skecobe.handlers.exceptions.NotFoundException;
import com.cashier.system.skecobe.repositories.InvoiceTourRepository;
import com.cashier.system.skecobe.repositories.OrderRepository;
import com.cashier.system.skecobe.requests.invoiceTour.CreateInvoiceTourRequest;
import com.cashier.system.skecobe.requests.invoiceTour.InvoiceTourRequestToReport;
import com.cashier.system.skecobe.requests.invoiceTour.UpdateInvoiceTourRequest;
import com.cashier.system.skecobe.responses.InvoiceTourOrderResponse;
import com.cashier.system.skecobe.responses.InvoiceTourResponse;
import com.cashier.system.skecobe.responses.OrderDetailsResponse;
import com.cashier.system.skecobe.responses.OrderResponse;
import com.cashier.system.skecobe.utils.ReportManager;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class InvoiceTourService {

    private InvoiceTourRepository invoiceTourRepository;
    private TourService tourService;
    private CashierService cashierService;
    private ValidationService validationService;
    private final OrderRepository orderRepository;

    public List<InvoiceTourResponse> getAll() {
        var response = invoiceTourRepository.findAll();
        return response.stream().map(InvoiceTourResponse::convertToResponse).toList();
    }

    public InvoiceTour getOneById(Long invoiceTourId) {
        return invoiceTourRepository.findById(invoiceTourId)
                .orElseThrow(() -> new NotFoundException("Invoice Tour"));
    }

    public Map<ProfitShared, Object> getDetail(Long invoiceTourId) {
        InvoiceTour invoiceTour = invoiceTourRepository.findById(invoiceTourId).orElseThrow(() -> new NotFoundException("Invoice Tour"));

        List<Order> orders = orderRepository.findByInvoiceTourId(invoiceTour);
        List<OrderResponse> orderResponse = orders.stream().map(OrderResponse::convertToResponse).toList();
        List<OrderDetailsResponse> orderDetails = new ArrayList<>();
        for (OrderResponse order : orderResponse) {
            List<OrderDetailsResponse> details = order.getOrderDetails();
            orderDetails.addAll(details);
        }
        return InvoiceTourOrderResponse.groupByProfitSharingType(orderDetails);
    }

    public InvoiceTourResponse getTourWithStatusAndTourId(Status status, Long tourId) {
        Tour tour = tourService.findById(tourId);
        var response = invoiceTourRepository.findByStatusAndTourId(status, tour);
        return (response != null)
                ? InvoiceTourResponse.convertToResponse(response)
                : null;
    }

    public List<InvoiceTourResponse> getTourWithStatus(Status status) {
        List<InvoiceTour> invoiceTours = invoiceTourRepository.findByStatus(status);
        return invoiceTours.stream().map(InvoiceTourResponse::convertToResponse).toList();
    }

    public InvoiceTourResponse save(CreateInvoiceTourRequest request) {
        validationService.validate(request);

        Tour tour = tourService.findById(request.getTourId());

        InvoiceTour invoiceTour = InvoiceTour.builder()
                .tourId(tour)
                .unitBus(request.getUnitBus())
                .profitSharing(0L)
                .employee(request.getEmployee())
                .status(Status.NOW)
                .build();

        invoiceTourRepository.save(invoiceTour);

        return InvoiceTourResponse.convertToResponse(invoiceTour);
    }

    public InvoiceTourResponse update(UpdateInvoiceTourRequest request) {
        InvoiceTour invoiceTour = invoiceTourRepository.findById(request.getInvoiceTourId())
                .orElseThrow(() -> new NotFoundException("Invoice Tour"));

        validationService.validate(request);

        invoiceTour.setTourId(tourService.findById(request.getTourId()));
        invoiceTour.setUnitBus(request.getUnitBus());
        invoiceTour.setProfitSharing(request.getProfitSharing());
        invoiceTour.setEmployee(request.getEmployee());
        invoiceTourRepository.save(invoiceTour);

        return InvoiceTourResponse.convertToResponse(invoiceTour);
    }

    public byte[] printInvoice(InvoiceTourRequestToReport request) throws JRException {
        ReportManager.getInstance().compileReport();
        InvoiceTour invoiceTour = invoiceTourRepository.findById(request.getInvoiceTourId())
                .orElseThrow(() -> new NotFoundException("Invoice Tour"));
        invoiceTour.setTourId(tourService.findById(request.getTourId()));
        invoiceTour.setUnitBus(request.getUnitBus());
        invoiceTour.setProfitSharing(request.getTotalProfitSharing());
        invoiceTour.setEmployee(request.getEmployee());
        invoiceTour.setStatus(Status.PREVIUSLY);
        invoiceTour.setUserId(cashierService.findById(request.getUserId()));
        invoiceTourRepository.save(invoiceTour);

        return ReportManager.getInstance().printReportInvoiceTour(request);
    }

//    public byte[] getInvoicePDF(Long invoiceTourId) throws JRException {
//        ReportManager.getInstance().compileReport();
//        InvoiceTour invoiceTour = invoiceTourRepository.findById(invoiceTourId).orElseThrow(() -> new NotFoundException("Product "));
//        var detail = getDetail(invoiceTourId);
//        Object profitSharingAmount = detail.get(ProfitShared.SHARING_AMOUNT);
//        for ()
////        InvoiceTourRequestToReport requestToReport = InvoiceTourRequestToReport.convertToReport(invoiceTour, detail);
////        return ReportManager.getInstance().printReportInvoiceTour(requestToReport);
//    }



    public void deleteById(Long productId) {
        invoiceTourRepository.deleteById(productId);
    }

}
