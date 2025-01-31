package com.bookingprojectn1.controller;

import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.CategoryDTO;
import com.bookingprojectn1.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Super Admin Category qushish")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        ApiResponse apiResponse = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.ok(apiResponse);
    }


    @Operation(summary = "Hamma categorylarni hammasini kurish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        ApiResponse allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }


    @Operation(summary = "Hamma bitta categoryni kurish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN')")
    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        ApiResponse categoryById = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryById);
    }


    @Operation(summary = "super admin categoryin update qilish")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId, @RequestParam String categoryName){
        ApiResponse apiResponse = categoryService.updateCategory(categoryId, categoryName);
        return ResponseEntity.ok(apiResponse);
    }


    @Operation(summary = "Super admin categoryni uchirish")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){
        ApiResponse apiResponse = categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.ok(apiResponse);
    }
}
