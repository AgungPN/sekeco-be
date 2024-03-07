package com.cashier.system.skecobe.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_order")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Component
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "invoiceTourId", referencedColumnName = "invoiceTourId")
    private InvoiceTour invoiceTourId;

    @Column(nullable = false)
    private int totalItems;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Long refund;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderDetails> orderDetails;

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
