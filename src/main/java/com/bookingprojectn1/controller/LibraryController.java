package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.FeedbackDTO;
import com.bookingprojectn1.payload.req.ReqLibrary;
import com.bookingprojectn1.security.CurrentUser;
import com.bookingprojectn1.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
@CrossOrigin
public class LibraryController {
    private final LibraryService libraryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Admin library qo'shish")
    @PostMapping
    public ResponseEntity<ApiResponse> saveLibrary(@RequestBody ReqLibrary reqLibrary) {
        ApiResponse apiResponse = libraryService.saveLibrary(reqLibrary);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN')")
    @Operation(summary = "Barcha search library")
    @GetMapping("/searchLibrary")
    public ResponseEntity<ApiResponse> searchLibrary(@RequestParam(value = "name" ,required = false) String name,
                                                     @RequestParam(value = "page" ,defaultValue = "0") int page,
                                                     @RequestParam(value = "size" ,defaultValue = "10") int size) {
        ApiResponse allLibraries = libraryService.getAllLibraries(name, page, size);
        return ResponseEntity.ok(allLibraries);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN')")
    @Operation(summary = "ADMIN/USER/LIBRARIAN bitta libraryni kurish")
    @GetMapping("/getOne/{libraryId}")
    public ResponseEntity<ApiResponse> getOneLibrary(@PathVariable Long libraryId) {
        ApiResponse library = libraryService.getOneLibrary(libraryId);
        return ResponseEntity.ok(library);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Admin libraryni update qilish")
    @PutMapping("/{libraryId}")
    public ResponseEntity<ApiResponse> updateLibrary(@PathVariable Long libraryId, @RequestBody ReqLibrary reqLibrary) {
        ApiResponse apiResponse = libraryService.updateLibrary(libraryId, reqLibrary);
        return ResponseEntity.ok(apiResponse);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Admin library delete qilish")
    @DeleteMapping("/{libraryId}")
    public ResponseEntity<ApiResponse> deleteLibrary(@PathVariable Long libraryId) {
        ApiResponse apiResponse = libraryService.deleteLibrary(libraryId);
        return ResponseEntity.ok(apiResponse);
    }



}
