package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Category;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.CategoryDTO;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ApiResponse saveCategory(CategoryDTO categoryDTO) {
        boolean b = categoryRepository.existsByNameIgnoreCase(categoryDTO.getName());
        if (b) {
            return new ApiResponse(ResponseError.ALREADY_EXIST("This category name"));
        }

        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();
        categoryRepository.save(category);
        return new ApiResponse("Successfully saved category");
    }


    public ApiResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category category : categories) {
            CategoryDTO categoryDTO = CategoryDTO.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();
            categoryDTOS.add(categoryDTO);
        }
        return new ApiResponse(categoryDTOS);
    }


    public ApiResponse getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Category "));
        }

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
        return new ApiResponse(categoryDTO);
    }


    public ApiResponse updateCategory(Long categoryId,String categoryName) {
        boolean b = categoryRepository.existsByNameIgnoreCase(categoryName);
        if (b) {
            return new ApiResponse(ResponseError.ALREADY_EXIST("This category name"));
        }

        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Category "));
        }

        category.setName(categoryName);
        categoryRepository.save(category);
        return new ApiResponse("Successfully updated category");
    }


    public ApiResponse deleteCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Category "));
        }

        categoryRepository.delete(category);
        return new ApiResponse("Successfully deleted Category");
    }
}
