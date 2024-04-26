package com.cashier.system.skecobe.entities;


import com.cashier.system.skecobe.enums.ProfitShared;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Column(nullable = false)
    private String name;

    private String brand;

    private Long profitSharing;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfitShared profitSharedType;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Integer stock;

    private LocalDate createdAt;
    private LocalDate lastModifiedAt;
    private LocalDate deletedAt = null;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedAt = LocalDate.now();
    }
}
