package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.BookReservationDTO;
import com.bookingprojectn1.security.CurrentUser;
import com.bookingprojectn1.service.BookOrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/book-reservation")
@RequiredArgsConstructor
public class BookOrderController {
    private final BookOrderService bookOrderService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @Operation(summary = "User oldindan order qilish")
    @PostMapping
    public ResponseEntity<ApiResponse> saveBookReservation(@RequestBody BookReservationDTO bookReservationDTO) {
        ApiResponse apiResponse = bookOrderService.reverseBook(bookReservationDTO);
        return ResponseEntity.ok(apiResponse);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN','ROLE_SUPER_ADMIN')")
    @Operation(summary = "Userning ijaraga olgan kitoblari muddatini tekshirish")
    @GetMapping("/checkReservation")
    public ResponseEntity<ApiResponse> checkBookNotification(@CurrentUser User user) {
        ApiResponse apiResponse = bookOrderService.checkNotification(user);
        return ResponseEntity.ok(apiResponse);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN','ROLE_SUPER_ADMIN')")
    @Operation(summary = "Userning ijaraga olgan kitoblarini search qilish")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> search(@RequestParam(required = false) Long user,
                                              @RequestParam(required = false) Long bookId,
                                              @RequestParam(required = false)LocalDate startDate,
                                              @RequestParam(required = false)LocalDate endDate,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        ApiResponse apiResponse = bookOrderService.search(user,bookId,startDate,endDate,page,size);
        return ResponseEntity.ok(apiResponse);
    }



    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "kitob buyicha muddati tugamagan orderlarini listini kurish")
    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse> getBookReservationList(@PathVariable Long bookId,
                                                              @CurrentUser User user) {
        ApiResponse reservationsByBook = bookOrderService.getReservationsByBook(bookId,user);
        return ResponseEntity.ok(reservationsByBook);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @Operation(summary = "BookReservationni kunini uzgartirish")
    @PutMapping("/{bookReservationId}")
    public ResponseEntity<ApiResponse> updateBookReservationList(@PathVariable Long bookReservationId,
                                                                 @RequestParam Long reservationDate) {
        ApiResponse reservationsByBook = bookOrderService.updateBookReservationDate(bookReservationId,reservationDate);
        return ResponseEntity.ok(reservationsByBook);
    }




    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @Operation(summary = "BookReservationni kunini uzgartirish")
    @DeleteMapping("/{bookReservationId}")
    public ResponseEntity<ApiResponse> deleteBookReservationList(@PathVariable Long bookReservationId) {
        ApiResponse reservationsByBook = bookOrderService.deleteReservation(bookReservationId);
        return ResponseEntity.ok(reservationsByBook);
    }
}
