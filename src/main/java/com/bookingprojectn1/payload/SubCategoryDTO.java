package com.bookingprojectn1.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryDTO {
    @Schema(hidden = true)
    private Long id;
    private String name;
    private Long categoryId;
}
