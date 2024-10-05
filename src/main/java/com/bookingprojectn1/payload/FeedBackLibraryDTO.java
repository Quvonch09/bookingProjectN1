package com.bookingprojectn1.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackLibraryDTO {
    private Integer id;
    private String message;
    private int ball;
    private String createdBy;
    private Long libraryId;
}
