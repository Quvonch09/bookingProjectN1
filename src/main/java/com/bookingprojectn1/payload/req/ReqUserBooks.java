package com.bookingprojectn1.payload.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqUserBooks {
    @Schema(hidden = true)
    private Long id;
    private Long bookId;
    @Schema(hidden = true)
    private String userName;
    private Long duration;
}
