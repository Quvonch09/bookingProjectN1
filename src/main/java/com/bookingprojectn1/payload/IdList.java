package com.bookingprojectn1.payload;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdList {
    private List<Long> ids;
}


