package com.bookingprojectn1.entity;

import com.bookingprojectn1.entity.enums.PayType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double paySum;
    private LocalDate payDate;
    @Enumerated(EnumType.STRING)
    private PayType payType;
    @ManyToOne
    private User payer;
    @ManyToOne
    private Library library;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
