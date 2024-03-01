package com.cashier.system.skecobe.requests.tour;

import com.cashier.system.skecobe.enums.TourCode;
import com.cashier.system.skecobe.validations.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UpdateTourRequest {
    private Long tourId;

    @NotBlank
    @Size(max = 200)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String address;

    @NotBlank
    @Size(max = 15, min = 11)
    private String phone;

    @EnumValidator(enumClazz = TourCode.class)
    private String tourCode;
}
