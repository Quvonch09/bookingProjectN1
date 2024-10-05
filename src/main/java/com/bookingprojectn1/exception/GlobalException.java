package com.bookingprojectn1.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.ResponseError;

import java.nio.file.AccessDeniedException;


@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> accessDeniedExceptionHandler(AccessDeniedException e) {
        return ResponseEntity.ok(new ApiResponse(ResponseError.ACCESS_DENIED()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> notFoundExceptionHandler(NotFoundException e) {
        return ResponseEntity.ok(e.getApiResponse());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.ok(new ApiResponse(ResponseError.VALIDATION_FAILED(message)));
    }
}
