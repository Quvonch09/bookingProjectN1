package com.bookingprojectn1.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouriteDTO {
    @Schema(hidden = true)
    private Integer id;
    private String createdBy;
    private LocalDateTime createdDate;
}
