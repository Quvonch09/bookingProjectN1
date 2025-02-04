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
    @Schema(hidden = true)
    private Long rate;
    private String description;
    private String author;
    private Integer pageCount;
    private String year;
    private Long fileId;
    private Long bookImgId;
    private Long libraryId;
    private Long subCategoryId;
    @Schema(hidden = true)
    private String bookStatus;
}
