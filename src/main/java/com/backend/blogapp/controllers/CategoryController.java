package com.backend.blogapp.controllers;

import com.backend.blogapp.exceptions.ResourceNotFoundException;
import com.backend.blogapp.payloads.ApiResponse;
import com.backend.blogapp.payloads.CategoryDto;
import com.backend.blogapp.services.CategoryService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //	create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){

        CategoryDto createdCatDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>( createdCatDto, HttpStatus.CREATED);
    }


    //	update
    @PutMapping("categoryId/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("categoryId") String catIdStr){
        if (!ObjectId.isValid(catIdStr)) {
            throw new ResourceNotFoundException("Invalid Category ID : " + catIdStr);
        }
        ObjectId catId = new ObjectId(catIdStr);

        CategoryDto updatedCatDto = this.categoryService.updateCategory(categoryDto,catId);
        return new ResponseEntity<CategoryDto>( updatedCatDto,HttpStatus.OK);

    }
    //	delete
    @DeleteMapping("categoryId/{categoryid}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryid") String catIdStr){
        if (!ObjectId.isValid(catIdStr)) {
            throw new ResourceNotFoundException("Invalid Category ID : " + catIdStr);
        }
        ObjectId catId = new ObjectId(catIdStr);

        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted successfully",true),HttpStatus.OK);

    }
    //	getCategoryById
    @GetMapping("categoryId/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") String catIdStr){
        if (!ObjectId.isValid(catIdStr)) {
            throw new ResourceNotFoundException("Invalid Category ID : " + catIdStr);
        }
        ObjectId catId = new ObjectId(catIdStr);

        CategoryDto catDto= this.categoryService.getCategoryById(catId);
        return new ResponseEntity<CategoryDto>(catDto,HttpStatus.OK);
    }

    //	getAllCategory
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categories= this.categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

}
