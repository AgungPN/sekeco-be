package com.cashier.system.skecobe.entities;


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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Column(nullable = false)
    private String name;

    private String brand;

    private Long profitSharingAmount;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Integer stock;

    private LocalDate createdAt;
    private LocalDate lastModifiedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedAt = LocalDate.now();
    }
}
