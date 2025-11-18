package com.example.amazonclonesystem.Controller;

import com.example.amazonclonesystem.ApiResponse.ApiResponse;
import com.example.amazonclonesystem.Model.Category;
import com.example.amazonclonesystem.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<?>  getCategories(){
        return (!categoryService.getCategories().isEmpty()) ? ResponseEntity.status(200).body(categoryService.getCategories())
                : ResponseEntity.status(400).body(new ApiResponse("Categories not found"));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("Category added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id,@RequestBody @Valid Category category,Errors errors) {
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        return (categoryService.updateCategory(id,category)) ? ResponseEntity.status(200).body(new ApiResponse("Category updated"))
                : ResponseEntity.status(400).body(new ApiResponse("Category with id: " + id + " not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        return (categoryService.deleteCategory(id)) ? ResponseEntity.status(200).body(new ApiResponse("Category deleted"))
                : ResponseEntity.status(400).body(new ApiResponse("Category with id: " + id + " not found"));

    }
}
