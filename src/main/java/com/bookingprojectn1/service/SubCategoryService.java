package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Category;
import com.bookingprojectn1.entity.SubCategory;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.payload.SubCategoryDTO;
import com.bookingprojectn1.repository.CategoryRepository;
import com.bookingprojectn1.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    public ApiResponse saveSubCategory(SubCategoryDTO subCategoryDTO) {
        Category category = categoryRepository.findById(subCategoryDTO.getCategoryId()).orElse(null);
        if (category == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Category"));
        }

        SubCategory subCategory = SubCategory.builder()
                .name(subCategoryDTO.getName())
                .category(category)
                .build();
        subCategoryRepository.save(subCategory);
        return new ApiResponse("Successfully saved SubCategory");
    }


    public ApiResponse getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        List<SubCategoryDTO> subCategoryDTOList = new ArrayList<>();

        for (SubCategory subCategory : subCategories) {
            SubCategoryDTO subCategoryDTO = SubCategoryDTO.builder()
                    .id(subCategory.getId())
                    .name(subCategory.getName())
                    .categoryId(subCategory.getCategory().getId())
                    .build();
            subCategoryDTOList.add(subCategoryDTO);
        }
        return new ApiResponse(subCategoryDTOList);
    }



    public ApiResponse getOneSubCategory(Long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElse(null);
        if (subCategory == null) {
            return new ApiResponse(ResponseError.NOTFOUND("SubCategory"));
        }

        SubCategoryDTO subCategoryDTO = SubCategoryDTO.builder()
                .id(subCategory.getId())
                .name(subCategory.getName())
                .categoryId(subCategory.getCategory().getId())
                .build();
        return new ApiResponse(subCategoryDTO);
    }


    public ApiResponse updateSubCategory(Long subCategoryId, SubCategoryDTO subCategoryDTO) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElse(null);
        if (subCategory == null) {
            return new ApiResponse(ResponseError.NOTFOUND("SubCategory"));
        }

        Category category = categoryRepository.findById(subCategoryDTO.getCategoryId()).orElse(null);
        if (category == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Category"));
        }

        subCategory.setId(subCategoryId);
        subCategory.setName(subCategoryDTO.getName());
        subCategory.setCategory(category);
        subCategoryRepository.save(subCategory);

        return new ApiResponse("Successfully updated SubCategory");
    }


    public ApiResponse deleteSubCategory(Long subCategoryId) {

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElse(null);
        if (subCategory == null) {
            return new ApiResponse(ResponseError.NOTFOUND("SubCategory"));
        }

        subCategoryRepository.delete(subCategory);

        return new ApiResponse("Successfully deleted SubCategory");
    }
}
