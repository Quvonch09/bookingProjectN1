package com.bookingprojectn1.controller;

import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.req.ReqUserBooks;
import com.bookingprojectn1.service.UserBooksService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userBooks")
@RequiredArgsConstructor
public class UserBooksController {
    private final UserBooksService userBooksService;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @Operation(summary = "Admin/Librarian userga ijaraga kitob berish uchun")
    @PostMapping
    public ResponseEntity<ApiResponse> saveUserBooks(@RequestBody ReqUserBooks reqUserBooks){
        ApiResponse apiResponse = userBooksService.saveUserBooks(reqUserBooks);
        return ResponseEntity.ok(apiResponse);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @Operation(summary = "Admin/Librarian userga ijaraga berilganlarni search qilish ")
    @GetMapping("/searchUserBooks")
    public ResponseEntity<ApiResponse> searchUserBooks(@RequestParam(value = "userName", required = false) String username,
                                                       @RequestParam(value = "bookId", required = false) Long bookId,
                                                       @RequestParam(value = "page" ,defaultValue = "0") int page,
                                                       @RequestParam(value = "size" ,defaultValue = "10") int size){
        ApiResponse apiResponse = userBooksService.searchUserBooks(bookId, username, page, size);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @Operation(summary = "Admin/Librarian userga ijaraga berilganlarni bittasini kurish")
    @GetMapping("/getOne/{userBookId}")
    public ResponseEntity<ApiResponse> getOneUserBook(@PathVariable Long userBookId){
        ApiResponse oneUserBooks = userBooksService.getOneUserBooks(userBookId);
        return ResponseEntity.ok(oneUserBooks);
    }



    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @Operation(summary = "Admin/Librarian userga ijaraga berilganlarni update qilish ")
    @PutMapping("/{userBookId}")
    public ResponseEntity<ApiResponse> updateUserBooks(@PathVariable Long userBookId, @RequestBody ReqUserBooks reqUserBooks){
        ApiResponse apiResponse = userBooksService.updateUserBooks(userBookId, reqUserBooks);
        return ResponseEntity.ok(apiResponse);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @Operation(summary = "Admin/Librarian userga ijaraga berilganlarni delete qilish ")
    @DeleteMapping("/{userBookId}")
    public ResponseEntity<ApiResponse> deleteUserBooks(@PathVariable Long userBookId){
        ApiResponse apiResponse = userBooksService.deleteUserBooks(userBookId);
        return ResponseEntity.ok(apiResponse);
    }
}
