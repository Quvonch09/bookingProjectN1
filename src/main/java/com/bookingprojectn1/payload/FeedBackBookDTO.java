package com.bookingprojectn1.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackBookDTO {
    private Integer id;
    private String message;
    private int ball;
    private String createdBy;
    private Long bookId;
}
