package com.bookingprojectn1.payload.res;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResNotification {

    private String title;

    private String content;
}


