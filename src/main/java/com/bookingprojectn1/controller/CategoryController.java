package com.bookingprojectn1.controller;

import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.CategoryDTO;
import com.bookingprojectn1.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Super Admin Category qushish")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping
    public ApiResponse saveCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.saveCategory(categoryDTO);
    }


    @Operation(summary = "Hamma categorylarni hammasini kurish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN')")
    @GetMapping("/all")
    public ApiResponse getAllCategories() {
        return categoryService.getAllCategories();
    }


    @Operation(summary = "Hamma bitta categoryni kurish")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN')")
    @GetMapping("/{categoryId}")
    public ApiResponse getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }


    @Operation(summary = "super admin categoryin update qilish")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/{categoryId}")
    public ApiResponse updateCategory(@PathVariable Long categoryId, @RequestParam String categoryName){
        return categoryService.updateCategory(categoryId, categoryName);
    }


    @Operation(summary = "Super admin categoryni uchirish")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ApiResponse deleteCategory(@PathVariable Long categoryId){
        return categoryService.deleteCategoryById(categoryId);
    }
}
