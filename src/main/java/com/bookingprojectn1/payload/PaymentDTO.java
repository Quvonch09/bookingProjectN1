package com.bookingprojectn1.payload;

import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.entity.enums.PayType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    @Schema(hidden = true)
    private Long id;
    private Double paySum;
    private LocalDate payDate;
    private String payType;
    private Long payerId;
    @Schema(hidden = true)
    private LocalDateTime createDate;
    @Schema(hidden = true)
    private LocalDateTime updateDate;
}
