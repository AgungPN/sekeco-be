package com.cashier.system.skecobe.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_order_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order orderId;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product productId;

    private Long profitSharingAmount;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Long subtotal;

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
