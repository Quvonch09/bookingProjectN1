package com.bookingprojectn1.payload.res;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResUserBooks {
    private Long id;
    private String userName;
    private Long bookId;
    private Long duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean submitted;
}
