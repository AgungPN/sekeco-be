package com.cashier.system.skecobe.entities;

import com.cashier.system.skecobe.enums.ProfitShared;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "order_details")
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
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Order orderId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "productId", referencedColumnName = "productId", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Product productId;

    private Long profitSharing;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfitShared profitSharedType;

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
