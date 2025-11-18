package com.example.amazonclonesystem.Controller;

import com.example.amazonclonesystem.ApiResponse.ApiResponse;
import com.example.amazonclonesystem.Model.Merchant;
import com.example.amazonclonesystem.Model.Product;
import com.example.amazonclonesystem.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProducts(){
        return (!productService.getProducts().isEmpty()) ? ResponseEntity.status(200).body(productService.getProducts())
                : ResponseEntity.status(400).body(new ApiResponse("Products not found"));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        return (productService.addProduct(product)) ? ResponseEntity.status(200).body(new ApiResponse("Product added"))
            : ResponseEntity.status(400).body(new ApiResponse("Category id not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id,@RequestBody @Valid Product product,Errors errors) {
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        return (productService.updateProduct(id,product)) ? ResponseEntity.status(200).body(new ApiResponse("Product updated"))
                : ResponseEntity.status(400).body(new ApiResponse("Product with id: " + id + " or Category Id not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        return (productService.deleteProduct(id)) ? ResponseEntity.status(200).body(new ApiResponse("Product deleted"))
                : ResponseEntity.status(400).body(new ApiResponse("Merchant with id: " + id + " not found"));
    }

    @GetMapping("/get-by-category/{categoryId}")
    public ResponseEntity<?> getByCategory(@PathVariable String categoryId) {
        return (!productService.getProductsByCategory(categoryId).isEmpty()) ? ResponseEntity.status(200).body(productService.getProductsByCategory(categoryId))
                : ResponseEntity.status(400).body(new ApiResponse("Category or Products not found"));
    }
}
