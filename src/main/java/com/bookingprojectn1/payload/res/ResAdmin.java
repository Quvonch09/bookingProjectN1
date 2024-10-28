package com.bookingprojectn1.payload.res;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResAdmin {
    private Long countAdmins;
    private Long countLibraries;
    private Long countUsers;
}
