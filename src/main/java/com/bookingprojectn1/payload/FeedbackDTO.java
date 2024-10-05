package com.bookingprojectn1.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDTO {
    @Schema(hidden = true)
    private Integer id;
    private String message;
    private int ball;
    @Schema(hidden = true)
    private String createdBy;
}
