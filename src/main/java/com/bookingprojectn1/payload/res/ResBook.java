package com.bookingprojectn1.payload.res;

import com.bookingprojectn1.payload.FeedbackDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResBook {
    private Long bookId;
    private String title;
    private String description;
    private Long rate;
    private String author;
    private Integer pageCount;
    private Long fileId;
    private Long libraryId;
    private Long categoryId;
    private String bookStatus;
    private List<FeedbackDTO> feedBackBook;
}
