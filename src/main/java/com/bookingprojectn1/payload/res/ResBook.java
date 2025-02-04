package com.bookingprojectn1.payload.res;

import com.bookingprojectn1.payload.FavouriteDTO;
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
    private String year;
    private Integer pageCount;
    private Long pdfId;
    private Long bookImgId;
    private Long libraryId;
    private Long categoryId;
    private Long subCategoryId;
    private String bookStatus;
    private Integer favouriteCount;
    private List<FeedbackDTO> feedBackBook;
    private List<FavouriteDTO> favouriteDTOList;
}
