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
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Long price;

    private Long profitSharingPercentage;
    private Long profitSharingAmount;

    private Integer stock;

    private String image;

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
