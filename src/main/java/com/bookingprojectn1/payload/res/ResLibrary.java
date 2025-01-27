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
public class ResLibrary {
    private Long libraryId;
    private String libraryName;
    private Long ownerId;
    private double lat;
    private double lng;
    private Long fileId;
    private Integer favouriteCount;
    private List<FavouriteDTO> favourites;
    private List<FeedbackDTO> feedBackLibraryDTOList;
}
