package com.backend.blogapp.seviceImpl;

import com.backend.blogapp.entities.Category;
import com.backend.blogapp.exceptions.ResourceNotFoundException;
import com.backend.blogapp.payloads.CategoryDto;
import com.backend.blogapp.repositries.CategoryRepo;
import com.backend.blogapp.services.CategoryService;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    private Category dtoToCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        return category;
    }
    private CategoryDto categoryToDto(Category category) {
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        return  categoryDto;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        if (categoryDto.getTitle() == null || categoryDto.getTitle().isEmpty()) {
            throw new ResourceNotFoundException("Title cannot be null or empty");
        }
        if (categoryDto.getDescription() == null || categoryDto.getDescription().isEmpty()) {
            throw new ResourceNotFoundException("Description cannot be null or empty");
        }
        Category category =dtoToCategory(categoryDto);
        Category savedUser= this.categoryRepo.save(category);
        return this.categoryToDto(savedUser);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, ObjectId categoryId) {
        Category oldCategory=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ","category Id ",categoryId.toString()));
        if (categoryDto.getTitle() == null || categoryDto.getTitle().isEmpty()) {
            throw new ResourceNotFoundException("Title cannot be null or empty");
        }
        if (categoryDto.getDescription() == null || categoryDto.getDescription().isEmpty()) {
            throw new ResourceNotFoundException("Description cannot be null or empty");
        }
        oldCategory.setTitle(categoryDto.getTitle());
        oldCategory.setDescription(categoryDto.getDescription());

        Category updatedCategory= this.categoryRepo.save(oldCategory);
        return this.categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(ObjectId categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ","category Id ",categoryId.toString()));
        this.categoryRepo.deleteById(category.getId());
    }

    @Override
    public CategoryDto getCategoryById(ObjectId categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ","category Id ",categoryId.toString()));
        return categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        List <CategoryDto> categoryDtos= categories.stream().map(category->this.categoryToDto(category)).collect(Collectors.toList());
        return categoryDtos;
    }
}
