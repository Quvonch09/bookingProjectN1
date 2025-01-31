package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.security.CurrentUser;
import com.bookingprojectn1.service.FavouriteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favourite")
public class FavouriteController {

    private final FavouriteService favouriteService;

    @Operation(summary = "Barcha userlar kitobga favourite bosish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @PostMapping("/book")
    public ResponseEntity<ApiResponse> saveFavouriteBook(@RequestParam Long bookId, @CurrentUser User user){
        ApiResponse apiResponse = favouriteService.saveBookFavourite(bookId, user);
        return ResponseEntity.ok(apiResponse);
    }


    @Operation(summary = "Barcha userlar kutubxonaga favourite bosish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @PostMapping("/library")
    public ResponseEntity<ApiResponse> saveFavouriteLibrary(@RequestParam Long libraryId, @CurrentUser User user){
        ApiResponse apiResponse = favouriteService.saveLibraryFavourite(libraryId, user);
        return ResponseEntity.ok(apiResponse);
    }


    @Operation(summary = "Barcha userlar kitobdan favourite qaytarib olish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @DeleteMapping("/book")
    public ResponseEntity<ApiResponse> deleteFavouriteBook(@RequestParam Long bookId, @CurrentUser User user){
        ApiResponse apiResponse = favouriteService.deleteBookFavourite(bookId, user);
        return ResponseEntity.ok(apiResponse);
    }



    @Operation(summary = "Barcha userlar kutubxonadan favourite qaytarib olish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @DeleteMapping("/library")
    public ResponseEntity<ApiResponse> deleteFavouriteLibrary(@RequestParam Long libraryId, @CurrentUser User user){
        ApiResponse apiResponse = favouriteService.deleteLibraryFavourite(libraryId, user);
        return ResponseEntity.ok(apiResponse);
    }
}
