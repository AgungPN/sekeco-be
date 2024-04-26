package com.cashier.system.skecobe.entities;

import com.cashier.system.skecobe.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Component
public class InvoiceTour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceTourId;

    @ManyToOne
    @JoinColumn(name = "tourId", referencedColumnName = "tourId", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tour tourId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User userId;

    @Column(nullable = false)
    private int unitBus;

    @Column(nullable = false)
    private Long profitSharing;

    @Column(nullable = false)
    private int employee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

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
