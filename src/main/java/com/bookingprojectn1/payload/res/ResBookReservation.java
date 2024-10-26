package com.bookingprojectn1.payload.res;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResBookReservation {
    private Long reservationId;
    private Long bookId;
    private Long userId;
    private LocalDate startReservationDate;
    private LocalDate endReservationDate;
    private Long leftDays;
}
