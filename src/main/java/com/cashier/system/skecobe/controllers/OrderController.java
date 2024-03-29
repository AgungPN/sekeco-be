package com.cashier.system.skecobe.controllers;

import com.cashier.system.skecobe.entities.Order;
import com.cashier.system.skecobe.requests.order.OrderRequest;
import com.cashier.system.skecobe.responses.ResponseHandler;
import com.cashier.system.skecobe.services.OrderService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Locale;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final TemplateEngine templateEngine;
    private final OrderService orderService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InputStreamResource> placeOrder(@RequestBody OrderRequest orderRequest, Locale locale) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf");

            byte[] reportBytes = orderService.saveOrder(orderRequest);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(new ByteArrayInputStream(reportBytes)));
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
//        return ResponseHandler.generateResponse("Order Create", orderResponse, HttpStatus.CREATED);
//        Context context = new Context();
//        context.setVariable("order", order);
//        context.setLocale(locale);
//        String orderHtml = templateEngine.process("invoice", context);
//
//        /* Setup Source and target I/O streams */
//        ByteArrayOutputStream target = new ByteArrayOutputStream();
//
//        /*Setup converter properties. */
//        ConverterProperties converterProperties = new ConverterProperties();
//        converterProperties.setBaseUri("http://localhost:3001");
//        /* Call convert method */
//        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);
//
//        /* extract output as bytes */
//        byte[] bytes = target.toByteArray();
//
//        /* Send the response as downloadable PDF */
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order.pdf")
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(bytes);
    }
}
