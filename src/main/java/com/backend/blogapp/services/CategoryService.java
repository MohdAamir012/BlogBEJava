package com.backend.blogapp.services;

import com.backend.blogapp.payloads.CategoryDto;
import org.bson.types.ObjectId;

import java.util.List;

public interface CategoryService {

    //	create category
    CategoryDto createCategory(CategoryDto categoryDto);

    //	update category
    CategoryDto updateCategory(CategoryDto categoryDto, ObjectId categoryId);

    //	Delete category
    void deleteCategory(ObjectId categoryId);

    //	Get category by ID
    CategoryDto getCategoryById(ObjectId categoryId);

    //	Get all category
    List<CategoryDto> getAllCategories();

}
