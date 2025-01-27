package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.security.CurrentUser;
import com.bookingprojectn1.service.FavouriteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.*;
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
    public ApiResponse saveFavouriteBook(@RequestParam Long bookId, @CurrentUser User user){
       return favouriteService.saveBookFavourite(bookId, user);
    }


    @Operation(summary = "Barcha userlar kutubxonaga favourite bosish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @PostMapping("/library")
    public ApiResponse saveFavouriteLibrary(@RequestParam Long libraryId, @CurrentUser User user){
        return favouriteService.saveLibraryFavourite(libraryId, user);
    }


    @Operation(summary = "Barcha userlar kitobdan favourite qaytarib olish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @DeleteMapping("/book")
    public ApiResponse deleteFavouriteBook(@RequestParam Long bookId, @CurrentUser User user){
        return favouriteService.deleteBookFavourite(bookId, user);
    }



    @Operation(summary = "Barcha userlar kutubxonadan favourite qaytarib olish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @DeleteMapping("/library")
    public ApiResponse deleteFavouriteLibrary(@RequestParam Long libraryId, @CurrentUser User user){
        return favouriteService.deleteLibraryFavourite(libraryId, user);
    }
}
