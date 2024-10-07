package com.bookingprojectn1.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(0) @Max(5)
    private int ball;
    @Schema(hidden = true)
    private String createdBy;
}
