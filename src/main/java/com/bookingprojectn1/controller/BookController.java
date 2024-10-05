package com.bookingprojectn1.controller;

import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.req.ReqBook;
import com.bookingprojectn1.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @Operation(summary = "ADMIN/LIBRARIAN book qo'shish")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PostMapping
    public ResponseEntity<ApiResponse> addBook(@RequestBody ReqBook book) {
        ApiResponse apiResponse = bookService.addBook(book);
        return ResponseEntity.ok(apiResponse);
    }


    @Operation(summary = "ADMIN/LIBRARIAN/USER search book")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @GetMapping("/search/book")
    public ResponseEntity<ApiResponse> searchBook(@RequestParam(value = "title", required = false) String title,
                                                  @RequestParam(value = "author", required = false) String author,
                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        ApiResponse allBooks = bookService.getAllBooks(title, author, page, size);
        return ResponseEntity.ok(allBooks);
    }

    @Operation(summary = "ADMIN/LIBRARIAN/USER bitta bookni kurish")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @GetMapping("/getOne/{bookId}")
    public ResponseEntity<ApiResponse> searchBook(@PathVariable Long bookId) {
        ApiResponse allBooks = bookService.getOneBook(bookId);
        return ResponseEntity.ok(allBooks);
    }


    @Operation(summary = "ADMIN/LIBRARIAN book update qilish")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse> updateBook(@PathVariable Long bookId,@RequestBody ReqBook book) {
        ApiResponse apiResponse = bookService.updateBook(bookId, book);
        return ResponseEntity.ok(apiResponse);
    }


    @Operation(summary = "ADMIN/LIBRARIAN book delete qilish")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long bookId) {
        ApiResponse apiResponse = bookService.deleteBook(bookId);
        return ResponseEntity.ok(apiResponse);
    }
}
