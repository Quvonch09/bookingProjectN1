package com.bookingprojectn1.payload.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqLibrary {
    @Schema(hidden = true)
    private Long id;
    private String libraryName;
    private String ownerPhoneNumber;
    private double lat;
    private double lng;
    private Long fileId;
}
