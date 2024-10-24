package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.security.CurrentUser;
import com.bookingprojectn1.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/followed")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;



    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN','ROLE_SUPER_ADMIN')")
    @Operation(summary = "ADMIN/USER/LIBRARIAN libraryga follow bosish")
    @PostMapping("/saveFollowed/{libraryId}")
    public ResponseEntity<ApiResponse> saveFollow(@PathVariable Long libraryId,
                                                  @CurrentUser User user){
        ApiResponse apiResponse = followService.saveFollow(libraryId, user);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN','ROLE_SUPER_ADMIN')")
    @Operation(summary = "ADMIN/USER/LIBRARIAN libraryga follow bosish")
    @GetMapping("/followListByLibrary/{libraryId}")
    public ResponseEntity<ApiResponse> getFollow(@PathVariable Long libraryId){
        ApiResponse apiResponse = followService.getFollowedByLibrary(libraryId);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN','ROLE_SUPER_ADMIN')")
    @Operation(summary = "Librarydan followni qaytarib olish")
    @DeleteMapping("/unfollow/{libraryId}")
    public ResponseEntity<ApiResponse> unfollow(@PathVariable Long libraryId,
                                                @CurrentUser User user){
        ApiResponse apiResponse = followService.deleteFollow(libraryId, user);
        return ResponseEntity.ok(apiResponse);
    }
}
