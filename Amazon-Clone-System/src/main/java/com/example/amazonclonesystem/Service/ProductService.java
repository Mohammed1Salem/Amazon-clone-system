package com.example.amazonclonesystem.Service;

import com.example.amazonclonesystem.Model.Category;
import com.example.amazonclonesystem.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class ProductService {

    ArrayList<Product> products = new ArrayList<>();

    private final CategoryService categoryService;

    public ArrayList<Product> getProducts(){
        return products;
    }

    public boolean addProduct(Product product){
        for(Category category : categoryService.getCategories()){
            if (category.getId().equals(product.getCategoryId())){
                products.add(product);
                return true;
            }
        }
        return false;
    }

    public boolean updateProduct(String id,Product product){
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)){
                products.set(i,product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id){
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> getLowestProductsByCategory(String categoryId) {
        ArrayList<Product> result = new ArrayList<>();

        for (Product product : products) {
            if (product.getCategoryId().equals(categoryId)) {
                result.add(product);
            }
        }

        result.sort(Comparator.comparing(Product::getPrice)) ;
        return result;
    }

    public ArrayList<Product> getProductsInPriceRange(double minPrice, double maxPrice) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                result.add(product);
            }
        }
        return result;
    }

    public ArrayList<Product> getHighestProductsByCategory(String categoryId) {
        ArrayList<Product> result = new ArrayList<>();

        for (Product product : products) {
            if (product.getCategoryId().equals(categoryId)) {
                result.add(product);
            }
        }

        result.sort(Comparator.comparing(Product::getPrice).reversed()) ;
        return result;
    }

}
