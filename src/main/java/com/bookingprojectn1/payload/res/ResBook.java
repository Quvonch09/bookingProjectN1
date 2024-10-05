package com.bookingprojectn1.payload.res;

import com.bookingprojectn1.entity.FeedBackForBook;
import com.bookingprojectn1.payload.FeedBackBookDTO;
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
    private List<FeedBackBookDTO> feedBackBook;
}
