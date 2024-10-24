package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.BookReservation;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.BookReservationDTO;
import com.bookingprojectn1.service.BookReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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



    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @Operation(summary = "kitob buyicha orderlarini listini kurish")
    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse> getBookReservationList(@PathVariable Long bookId) {
        ApiResponse reservationsByBook = bookReservationService.getReservationsByBook(bookId);
        return ResponseEntity.ok(reservationsByBook);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @Operation(summary = "BookReservationni kunini uzgartirish")
    @PutMapping("/{bookReservationId}")
    public ResponseEntity<ApiResponse> updateBookReservationList(@PathVariable Long bookReservationId,
                                                                 @RequestParam LocalDate reservationDate) {
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
