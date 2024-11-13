package com.bookingprojectn1.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class BookReservationDTO {
    private Long bookId;
    private Long userId;
    private LocalDate startDate;
    private Long duration;
}
