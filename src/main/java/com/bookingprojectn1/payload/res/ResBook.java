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
    private String author;
    private Integer pageCount;
    private Long fileId;
    private List<FeedbackDTO> feedBackBook;
}
