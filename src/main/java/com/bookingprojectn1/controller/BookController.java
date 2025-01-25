package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.enums.BookStatus;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.req.ReqBook;
import com.bookingprojectn1.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
@CrossOrigin
public class BookController {
    private final BookService bookService;

    @Operation(summary = "ADMIN/LIBRARIAN book qo'shish")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PostMapping
    public ResponseEntity<ApiResponse> addBook(@RequestBody ReqBook book) {
        ApiResponse apiResponse = bookService.addBook(book);
        return ResponseEntity.ok(apiResponse);
    }


    @Operation(summary = "Barcha rollar search book")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @GetMapping("/search/book")
    public ResponseEntity<ApiResponse> searchBook(@RequestParam(value = "title", required = false) String title,
                                                  @RequestParam(value = "description", required = false) String description,
                                                  @RequestParam(value = "author", required = false) String author,
                                                  @RequestParam(value = "libraryId", required = false) Long libraryId,
                                                  @RequestParam(value = "categoryId", required = false) Long categoryId,
                                                  @RequestParam(value = "bookStatus") BookStatus bookStatus,
                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        ApiResponse allBooks = bookService.getAllBooks(title,description, author,libraryId,categoryId,bookStatus, page, size);
        return ResponseEntity.ok(allBooks);
    }

    @Operation(summary = "Barcha rollar bitta bookni kurish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @GetMapping("/getOne/{bookId}")
    public ResponseEntity<ApiResponse> getOneBook(@PathVariable Long bookId) {
        ApiResponse allBooks = bookService.getOneBook(bookId);
        return ResponseEntity.ok(allBooks);
    }


    @Operation(summary = "ADMIN/LIBRARIAN book update qilish")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse> updateBook(@PathVariable Long bookId,
                                                  @RequestBody ReqBook book,
                                                  @RequestParam BookStatus status)  {
        ApiResponse apiResponse = bookService.updateBook(bookId, book,status);
        return ResponseEntity.ok(apiResponse);
    }



    @Operation(summary = "ADMIN/LIBRARIAN book status update qilish")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PutMapping("/updateStatus/{bookId}")
    public ResponseEntity<ApiResponse> updateBookStatus(@PathVariable Long bookId,
                                                  @RequestParam BookStatus status)  {
        ApiResponse apiResponse = bookService.updateStatusBook(bookId,status);
        return ResponseEntity.ok(apiResponse);
    }


    @Operation(summary = "ADMIN/LIBRARIAN book delete qilish")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long bookId) {
        ApiResponse apiResponse = bookService.deleteBook(bookId);
        return ResponseEntity.ok(apiResponse);
    }




    @Operation(summary = "Barcha rollar booklarning reytingini kurish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_LIBRARIAN','ROLE_USER')")
    @GetMapping("/ratingBooks")
    public ResponseEntity<ApiResponse> getRatingBook(){
        ApiResponse apiResponse = bookService.rateBook();
        return ResponseEntity.ok(apiResponse);
    }
}
