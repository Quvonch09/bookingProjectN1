package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.BookReservation;
import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.BookReservationDTO;
import com.bookingprojectn1.security.CurrentUser;
import com.bookingprojectn1.service.BookReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;

@RestController
@RequestMapping("/book-reservation")
@RequiredArgsConstructor
public class BookReservationController {
    private final BookReservationService bookReservationService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @Operation(summary = "User oldindan order qilish")
    @PostMapping
    public ResponseEntity<ApiResponse> saveBookReservation(@RequestBody BookReservationDTO bookReservationDTO) {
        ApiResponse apiResponse = bookReservationService.reverseBook(bookReservationDTO);
        return ResponseEntity.ok(apiResponse);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN','ROLE_SUPER_ADMIN')")
    @Operation(summary = "Userning ijaraga olgan kitoblari muddatini tekshirish")
    @GetMapping("/checkReservation")
    public ResponseEntity<ApiResponse> checkBookNotification(@CurrentUser User user) {
        ApiResponse apiResponse = bookReservationService.checkNotification(user);
        return ResponseEntity.ok(apiResponse);
    }



    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "kitob buyicha muddati tugamagan orderlarini listini kurish")
    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse> getBookReservationList(@PathVariable Long bookId,
                                                              @CurrentUser User user) {
        ApiResponse reservationsByBook = bookReservationService.getReservationsByBook(bookId,user);
        return ResponseEntity.ok(reservationsByBook);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @Operation(summary = "BookReservationni kunini uzgartirish")
    @PutMapping("/{bookReservationId}")
    public ResponseEntity<ApiResponse> updateBookReservationList(@PathVariable Long bookReservationId,
                                                                 @RequestParam Long reservationDate) {
        ApiResponse reservationsByBook = bookReservationService.updateBookReservationDate(bookReservationId,reservationDate);
        return ResponseEntity.ok(reservationsByBook);
    }




    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @Operation(summary = "BookReservationni kunini uzgartirish")
    @DeleteMapping("/{bookReservationId}")
    public ResponseEntity<ApiResponse> deleteBookReservationList(@PathVariable Long bookReservationId) {
        ApiResponse reservationsByBook = bookReservationService.deleteReservation(bookReservationId);
        return ResponseEntity.ok(reservationsByBook);
    }
}
