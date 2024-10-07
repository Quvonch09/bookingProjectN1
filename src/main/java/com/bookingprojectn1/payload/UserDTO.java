package com.bookingprojectn1.payload;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @Schema(hidden = true)
    private Long userId;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Schema(hidden = true)
    private String role;

    private Long fileId;
}
