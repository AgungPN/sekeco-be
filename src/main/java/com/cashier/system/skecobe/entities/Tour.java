package com.cashier.system.skecobe.entities;

import com.cashier.system.skecobe.enums.TourCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long tourId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TourCode tourCode;

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
