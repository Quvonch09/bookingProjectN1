package com.bookingprojectn1.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowedDTO {
    @Schema(hidden = true)
    private Integer id;
    private String userName;
    private LocalDate followedDate;
}
