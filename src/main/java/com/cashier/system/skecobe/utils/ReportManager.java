package com.cashier.system.skecobe.utils;

import com.cashier.system.skecobe.requests.invoiceTour.InvoiceTourRequestToReport;
import com.cashier.system.skecobe.responses.OrderDetailToReport;
import com.cashier.system.skecobe.responses.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class ReportManager {

    private static ReportManager instance;

    private JasperReport invoiceCashier;
    private JasperReport invoiceTour;

    public static ReportManager getInstance() {
        if (instance == null) {
            instance = new ReportManager();
        }
        return instance;
    }

    public void compileReport() throws JRException {
        invoiceCashier = JasperCompileManager.compileReport(getClass().getResourceAsStream("/templates/jasperReports/InvoiceCashier.jrxml"));
        invoiceTour = JasperCompileManager.compileReport(getClass().getResourceAsStream("/templates/jasperReports/InvoiceTour.jrxml"));
    }

    public byte[] printReportPayment(OrderResponse data) throws JRException {
        Map parametermeter = new HashMap();
        parametermeter.put("transaksi", data.getOrderId());
        parametermeter.put("kasir", data.getUserId().getUsername());
        parametermeter.put("totalItems", data.getTotalItems());
        parametermeter.put("totalPrice", data.getTotalPrice());
        parametermeter.put("amount", data.getAmount());
        parametermeter.put("refund", data.getRefund());
        List<OrderDetailToReport> list = new ArrayList<>();
        for(int i = 0; i < data.getOrderDetails().size(); i++){
            list.add(OrderDetailToReport.convertToDataReport(data.getOrderDetails().get(i)));
        }
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = JasperFillManager.fillReport(invoiceCashier, parametermeter, dataSource);
        byte[] pdfBytes = exportPdf(print);
        return pdfBytes;
    }

    public byte[] printReportInvoiceTour(InvoiceTourRequestToReport data) throws JRException {
        Map parametermeter = new HashMap();
        parametermeter.put("unitBus", data.getUnitBus());
        parametermeter.put("employeeAmount", data.getEmployeeAmount());
        parametermeter.put("omset", data.getOmset());
        parametermeter.put("tourId", data.getTourId());
        parametermeter.put("employee", data.getEmployee());
        parametermeter.put("total", data.getTotalProfitSharing());
        parametermeter.put("tourName", data.getTourName());

        JRBeanCollectionDataSource ProfitSharingAmount = new JRBeanCollectionDataSource(data.getProfitSharingAmounts());
        JRBeanCollectionDataSource DiscDataset = new JRBeanCollectionDataSource(data.getProfitSharingPercentages());
        parametermeter.put("ProfitSharingAmount", ProfitSharingAmount);
        parametermeter.put("DiscDataset", DiscDataset);
        JasperPrint print = JasperFillManager.fillReport(invoiceTour, parametermeter, new JREmptyDataSource());
        byte[] pdfBytes = exportPdf(print);
        return pdfBytes;
    }

    private byte[] exportPdf(JasperPrint print) throws JRException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
