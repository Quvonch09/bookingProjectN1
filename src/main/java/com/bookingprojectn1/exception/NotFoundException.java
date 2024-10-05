package com.bookingprojectn1.exception;

import com.bookingprojectn1.payload.ApiResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException {

    private ApiResponse apiResponse;
    public NotFoundException(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }

}
