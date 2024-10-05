package com.bookingprojectn1.payload.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqBook {
    @Schema(hidden = true)
    private Long bookId;
    private String title;
    private String author;
    private Integer pageCount;
    private Long fileId;
    @Schema(hidden = true)
    private Long libraryId;
}
