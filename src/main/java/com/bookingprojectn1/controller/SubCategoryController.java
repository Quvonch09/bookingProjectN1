package com.bookingprojectn1.controller;

import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.CategoryDTO;
import com.bookingprojectn1.payload.SubCategoryDTO;
import com.bookingprojectn1.service.CategoryService;
import com.bookingprojectn1.service.SubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subCategory")
@RequiredArgsConstructor
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @Operation(summary = "Super Admin SubCategory qushish")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> saveCategory(@RequestBody SubCategoryDTO categoryDTO) {
        ApiResponse apiResponse = subCategoryService.saveSubCategory(categoryDTO);
        return ResponseEntity.ok(apiResponse);
    }


    @Operation(summary = "Hamma subCategorylarni hammasini kurish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        ApiResponse allCategories = subCategoryService.getAllSubCategories();
        return ResponseEntity.ok(allCategories);
    }


    @Operation(summary = "Hamma bitta subCategoryni kurish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN')")
    @GetMapping("/{subCategoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long subCategoryId) {
        ApiResponse categoryById = subCategoryService.getOneSubCategory(subCategoryId);
        return ResponseEntity.ok(categoryById);
    }


    @Operation(summary = "super admin subCategoryni update qilish")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/{subCategoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long subCategoryId, @RequestBody SubCategoryDTO categoryDTO){
        ApiResponse apiResponse = subCategoryService.updateSubCategory(subCategoryId, categoryDTO);
        return ResponseEntity.ok(apiResponse);
    }


    @Operation(summary = "Super admin subCategoryni uchirish")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/{subCategoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long subCategoryId){
        ApiResponse apiResponse = subCategoryService.deleteSubCategory(subCategoryId);
        return ResponseEntity.ok(apiResponse);
    }
}
