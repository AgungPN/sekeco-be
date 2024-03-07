package com.cashier.system.skecobe.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "supplierId", referencedColumnName = "supplierId")
    private Supplier supplier;

    @Column(nullable = false)
    private Integer totalItems;

    @Column(nullable = false)
    private Long totalPrice;

    private Integer discount = 0;

    @Column(nullable = false)
    private Long amount;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
}
