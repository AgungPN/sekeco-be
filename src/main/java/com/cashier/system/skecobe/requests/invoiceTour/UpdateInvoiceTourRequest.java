package com.cashier.system.skecobe.requests.invoiceTour;

import com.cashier.system.skecobe.entities.Tour;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UpdateInvoiceTourRequest {
    private Long invoiceTourId;

    private Long tourId;
    @NotNull
    @Positive
    private int unitBus;
    private Long profitSharing;
    @NotNull
    @Positive
    private int employee;
}
