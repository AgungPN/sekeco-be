package com.cashier.system.skecobe.requests;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class CreateProductRequest {
    @NotBlank
    @Size(max = 200)
    private String barcode;

    @NotBlank
    @Size(max = 200)
    private String name;

    private String brand;

    @Min(0)
    private Long profitSharingAmount;

    @Min(0)
    private Long price;


    @Min(0)
    private Integer stock;
}
