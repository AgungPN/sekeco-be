package com.cashier.system.skecobe.requests.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UpdateProductRequest {
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String barcode;

    @NotBlank
    @Size(max = 200)
    private String name;

    private String brand;

    @NotNull
    private String profitSharedType;

    @Min(0)
    private Long profitSharing;

    @Min(0)
    private Long price;

    @Min(0)
    private Integer stock;
}
