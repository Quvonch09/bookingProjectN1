package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.FeedbackDTO;
import com.bookingprojectn1.security.CurrentUser;
import com.bookingprojectn1.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Operation(summary = "ADMIN/LIBRARIAN/USER bookga feedback berish uchun")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @PostMapping("/forBook/{bookId}")
    public ResponseEntity<ApiResponse> saveFeedbackBook(@PathVariable Long bookId,
                                                    @CurrentUser User user,
                                                    @RequestBody FeedbackDTO feedbackDTO){
        ApiResponse apiResponse = feedbackService.saveFeedbackBook(bookId, feedbackDTO, user);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "ADMIN/LIBRARIAN/USER libraryga feedback berish uchun")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @PostMapping("/forLibrary/{bookId}")
    public ResponseEntity<ApiResponse> saveFeedbackLibrary(@PathVariable Long bookId,
                                                    @CurrentUser User user,
                                                    @RequestBody FeedbackDTO feedbackDTO){
        ApiResponse apiResponse = feedbackService.saveFeedbackLibrary(bookId, feedbackDTO, user);
        return ResponseEntity.ok(apiResponse);
    }
}
